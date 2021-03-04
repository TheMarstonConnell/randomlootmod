package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.mojang.datafixers.schemas.Schema;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.datafix.fixes.TippedArrow;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.StatBoostTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;

public class RLBowItem extends RLShootableItem implements IRLTool {

	public RLBowItem(String name) {
		super(new Properties());
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));

		if (FMLEnvironment.dist == Dist.CLIENT) {
			TextureProxy.setModelProperties(this);
		}
		RLItems.ITEMS.add(this);
	}

	private float getVelo(ItemStack stack) {
		return BaseTool.getFloatNBT(stack, "rl_velo");

	}

	private void setVelo(ItemStack stack, float velo) {
		BaseTool.setFloatNBT(stack, "rl_velo", velo);

	}

	public void addVelo(float num, ItemStack stack) {
		setVelo(stack, getVelo(stack) + num);

	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (!(entityLiving instanceof PlayerEntity)) {
			return;
		}

		// set up player and abilities
		PlayerEntity playerentity = (PlayerEntity) entityLiving;
		boolean infinite = playerentity.abilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		ItemStack itemstack = playerentity.findAmmo(stack); // ammo

		int i = this.getUseDuration(stack) - timeLeft;

		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i,
				!itemstack.isEmpty() || infinite);
		if (i < 0)
			return;

		if (itemstack.isEmpty() && !infinite) { // can't shoot
			return;
		}
		
		

		if (itemstack.isEmpty()) {
			itemstack = new ItemStack(Items.ARROW);
		}

		float f = getArrowVelocity(i);
		if (!((double) f < 0.1D)) {
			boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem
					&& ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
			if (!worldIn.isRemote) {

				ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem()
						: Items.ARROW);

				double dmgMod = BaseTool.getFloatNBT(stack, "rl_bow_dmg");

				ArrowEntity abstractarrowentity = (ArrowEntity) arrowitem.createArrow(worldIn, itemstack, playerentity);


				abstractarrowentity = customArrow(stack, abstractarrowentity);

				abstractarrowentity.setDamage(abstractarrowentity.getDamage() * dmgMod);
				
				
				abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw,
						0.0F, f * 3.0F, 1.0F);
				if (f == 1.0F) {
					abstractarrowentity.setIsCritical(true);
				}

				int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (j > 0) {
					abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double) j * 0.5D + 0.5D);
				}

				int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
				if (k > 0) {
					abstractarrowentity.setKnockbackStrength(k);
				}

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
					abstractarrowentity.setFire(100);
				}

				stack.damageItem(1, playerentity, (p_220009_1_) -> {
					p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
				});
				if (flag1 || playerentity.abilities.isCreativeMode
						&& (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
					abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
				}
				
				if(!TagHelper.getTagList(stack).isEmpty()) {
					abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
				}
				

				worldIn.addEntity(abstractarrowentity);
				BaseTool.changeXP(stack, 1, worldIn, entityLiving.getPosition());
				BaseTool.setLore(stack);

			}

			worldIn.playSound((PlayerEntity) null, playerentity.getPosX(), playerentity.getPosY(),
					playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
					1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			if (!flag1 && !playerentity.abilities.isCreativeMode) {
				itemstack.shrink(1);
				if (itemstack.isEmpty()) {
					playerentity.inventory.deleteStack(itemstack);
				}
			}

			playerentity.addStat(Stats.ITEM_USED.get(this));

		}

	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return (int) (getVelo(stack));
	}

	/**
	 * returns the action that specifies what animation to play when the items is
	 * being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		boolean flag = playerIn.findAmmo(itemstack).isEmpty();
		System.out.println("has arrow? " + !flag);

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn,
				playerIn, handIn, !flag);
		if (ret != null)
			return ret;

		System.out.println("shooting");

		if (!playerIn.abilities.isCreativeMode && flag) {
			System.out.println("fail");
			return ActionResult.resultFail(itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemstack);
		}
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory,
	 * not their main/offhand
	 */
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return ARROWS;
	}

	public ArrowEntity customArrow(ItemStack stack, ArrowEntity arrow) {

		List<BasicTag> tags = TagHelper.getTagList(stack);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				if (eTag.offensive) {

					arrow.addEffect(eTag.getEffect());
				}
			}
		}

		return arrow;
	}

	public int func_230305_d_() {
		return 15;
	}

	@Override
	public void setStats(ItemStack stack) {
		setVelo(stack, 72000.0f);
		BaseTool.setIntNBT(stack, "rl_level", 1);
		BaseTool.setFloatNBT(stack, "rl_bow_dmg", 1.0f);
	}

	@Override
	public void updateStats(ItemStack stack) {
		// Nothing for now
	}

	@Override
	public void upgradeTool(ItemStack stack) {
		setVelo(stack, getVelo(stack) * 0.95f);
		BaseTool.setFloatNBT(stack, "rl_bow_dmg", BaseTool.getFloatNBT(stack, "rl_bow_dmg") * 1.2f);

	}

	@Override
	public List<String> getStatsLore(ItemStack stack) {
		DecimalFormat f = new DecimalFormat("#0.00");

		List<String> s = new ArrayList<String>();
		s.add(TextFormatting.GRAY + "Pull Speed: " + f.format(getVelo(stack) / 72000.0f * 100) + "%");
		s.add(TextFormatting.GRAY + "Bow Damage Modifier: " + f.format(BaseTool.getFloatNBT(stack, "rl_bow_dmg")));

		return s;
	}

	@Override
	public String getItemType() {
		return "bow";
	}

	@Override
	public int getVariants() {
		return 6;
	}

	@Override
	public List<BasicTag> getAllowedTags() {

		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			}else if (tag instanceof StatBoostTag) {
				StatBoostTag eTag = (StatBoostTag) tag;
				if (eTag.forTools) {
					allowedTags.add(eTag);
				}
			}else if (tag instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tag;
				if (eTag.forTools) {
					allowedTags.add(eTag);
				}
			}

		}

		allowedTags.add(TagHelper.UNBREAKABLE);
		allowedTags.add(TagHelper.REPLENISH);

		return allowedTags;
	}

}
