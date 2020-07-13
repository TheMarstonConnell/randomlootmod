package xyz.marstonconnell.randomloot.tools;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.entity.ThrowableWeaponEntity;
import xyz.marstonconnell.randomloot.init.RLEntities;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RLThrowableItem extends RLBowItem implements IRLTool {

	public RLThrowableItem(String name) {
		super(name);
		RLItems.ITEMS.add(this);
	}

	@Override
	public void setStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		int dmg = Config.BASE_THROWABLE_DAMAGE.get();

		nbt.putInt("rl_damage", dmg);

		stack.setTag(nbt);

	}

	@Override
	public void updateStats(ItemStack stack) {

	}

	@Override
	public void upgradeTool(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		int dmg = nbt.getInt("rl_damage");

		nbt.putInt("rl_damage", dmg + 1);

		stack.setTag(nbt);
	}

	@Override
	public List<String> getStatsLore(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		List<String> s = new ArrayList<String>();
		s.add(TextFormatting.GRAY + "Attack Damage: " + nbt.getInt("rl_damage"));

		return s;
	}

	@Override
	public String getItemType() {
		return "throwable";
	}

	@Override
	public int getVariants() {
		return 10;
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
			} else if (tag instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tag;
				if (eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			}

		}

		allowedTags.add(TagHelper.UNBREAKABLE);
		allowedTags.add(TagHelper.REPLENISH);

		return allowedTags;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		boolean flag = false;
		System.out.println("has ammo? " + !flag);

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

	public static float getVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	/**
	 * returns the action that specifies what animation to play when the items is
	 * being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}
	
	public ThrowableWeaponEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ThrowableWeaponEntity arrowentity = new ThrowableWeaponEntity(worldIn, shooter);
		arrowentity.setPotionEffect(stack);
		return arrowentity;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			boolean flag = playerentity.abilities.isCreativeMode;

			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i,
					flag);
			if (i < 0)
				return;

			if ( flag) {
				
				changeXP(stack, 1);
				
				
				
				
				float f = getArrowVelocity(i);
				if (!((double) f < 0.1D)) {
					boolean flag1 = playerentity.abilities.isCreativeMode;
					if (!worldIn.isRemote) {
						
						
						
						ArrowItem arrowitem = (ArrowItem) Items.ARROW;
						
						
						
						
						ArrowEntity abstractarrowentity = createArrow(worldIn, new ItemStack(arrowitem),
								playerentity);
						
						
						
						
						
						abstractarrowentity = customArrow(stack, abstractarrowentity);
						
						abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch,
								playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
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
						if (flag1
								|| playerentity.abilities.isCreativeMode) {
							abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
						}
						
						ThrowableWeaponEntity arrow = (ThrowableWeaponEntity) abstractarrowentity;
						arrow.item = stack;
						
						worldIn.addEntity(abstractarrowentity);
					}

					worldIn.playSound((PlayerEntity) null, playerentity.getPosX(), playerentity.getPosY(),
							playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					

					playerentity.addStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
}
