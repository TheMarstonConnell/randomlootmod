package xyz.marstonconnell.randomloot.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.entity.ThrowableWeaponEntity;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RLThrowableItem extends BaseTool implements IRLTool {

	public RLThrowableItem(String name) {
		super(new Properties());
		RLItems.ITEMS.add(this);
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));

		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 8.0D,
				AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier",
				(double) -2.9F, AttributeModifier.Operation.ADDITION));
		this.field_234812_a_ = builder.build();
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
		setIntNBT(stack, "rl_level", 1);
		stack.setTag(nbt);

	}

	@Override
	public void updateStats(ItemStack stack) {

	}

	@Override
	public void upgradeTool(ItemStack stack, World worldIn) {
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

	private final Multimap<Attribute, AttributeModifier> field_234812_a_;

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	/**
	 * returns the action that specifies what animation to play when the items is
	 * being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse
	 * button).
	 */
//	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
//		if (entityLiving instanceof PlayerEntity) {
//			PlayerEntity playerentity = (PlayerEntity) entityLiving;
//			int i = this.getUseDuration(stack) - timeLeft;
//			if (i >= 10) {
//				int j = EnchantmentHelper.getRiptideModifier(stack);
//				if (j <= 0 || playerentity.isWet()) {
//					if (!worldIn.isRemote) {
//						stack.damageItem(1, playerentity, (p_220047_1_) -> {
//							p_220047_1_.sendBreakAnimation(entityLiving.getActiveHand());
//						});
//						if (j == 0) {
//							ThrowableWeaponEntity tridententity = new ThrowableWeaponEntity(worldIn, playerentity, stack);
//							tridententity.setItem(stack);
//							tridententity.func_234612_a_(playerentity, playerentity.rotationPitch,
//									playerentity.rotationYaw, 0.0F, 2.5F + (float) j * 0.5F, 1.0F);
//							if (playerentity.abilities.isCreativeMode) {
//								tridententity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//							}
//
//							worldIn.addEntity(tridententity);
//							worldIn.playMovingSound((PlayerEntity) null, tridententity, SoundEvents.ITEM_TRIDENT_THROW,
//									SoundCategory.PLAYERS, 1.0F, 1.0F);
//							if (!playerentity.abilities.isCreativeMode) {
//								playerentity.inventory.deleteStack(stack);
//							}
//						}
//					}
//
//					playerentity.addStat(Stats.ITEM_USED.get(this));
//					if (j > 0) {
//						float f7 = playerentity.rotationYaw;
//						float f = playerentity.rotationPitch;
//						float f1 = -MathHelper.sin(f7 * ((float) Math.PI / 180F))
//								* MathHelper.cos(f * ((float) Math.PI / 180F));
//						float f2 = -MathHelper.sin(f * ((float) Math.PI / 180F));
//						float f3 = MathHelper.cos(f7 * ((float) Math.PI / 180F))
//								* MathHelper.cos(f * ((float) Math.PI / 180F));
//						float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
//						float f5 = 3.0F * ((1.0F + (float) j) / 4.0F);
//						f1 = f1 * (f5 / f4);
//						f2 = f2 * (f5 / f4);
//						f3 = f3 * (f5 / f4);
//						playerentity.addVelocity((double) f1, (double) f2, (double) f3);
//						playerentity.startSpinAttack(20);
//						if (playerentity.func_233570_aj_()) {
//							float f6 = 1.1999999F;
//							playerentity.move(MoverType.SELF, new Vector3d(0.0D, (double) 1.1999999F, 0.0D));
//						}
//
//						SoundEvent soundevent;
//						if (j >= 3) {
//							soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
//						} else if (j == 2) {
//							soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
//						} else {
//							soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
//						}
//
//						worldIn.playMovingSound((PlayerEntity) null, playerentity, soundevent, SoundCategory.PLAYERS,
//								1.0F, 1.0F);
//					}
//
//				}
//			}
//		}
//	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
			return ActionResult.resultFail(itemstack);
		} else if (EnchantmentHelper.getRiptideModifier(itemstack) > 0 && !playerIn.isWet()) {
			return ActionResult.resultFail(itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemstack);
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the entry
	 * argument beside ev. They just raise the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (p_220048_0_) -> {
			p_220048_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the
	 * "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving, (p_220046_0_) -> {
				p_220046_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.field_234812_a_
				: super.getAttributeModifiers(equipmentSlot);
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on
	 * material.
	 */
	public int getItemEnchantability() {
		return 1;
	}
}
