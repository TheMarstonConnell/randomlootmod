package xyz.marstonconnell.randomloot.tools;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;

public class RLToolItem extends ToolItem {
	private final Set<Block> effectiveBlocks;
	protected final float efficiency;
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> field_234674_d_;

	public RLToolItem(String name, Set<Block> effectiveBlocksIn, float attackDamageIn, float attackSpeedIn) {
		super(attackDamageIn, attackSpeedIn, ItemTier.DIAMOND, effectiveBlocksIn, new Properties());
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));

		this.effectiveBlocks = effectiveBlocksIn;
		this.efficiency = 8.0f;
		this.attackDamage = attackDamageIn;

		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier",
				(double) attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.field_234674_d_ = builder.build();

		
		if(FMLEnvironment.dist == Dist.CLIENT) {
            TextureProxy.setModelProperties(this);
        }
		RLItems.ITEMS.add(this);
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isRepairable(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return repair.getItem().equals(RLItems.best_shard);

	}

	public float getDestroySpeed(ItemStack stack, BlockState state) { // TODO add random speed
		if (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e)))
			return efficiency;
		return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiency : 1.0F;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry
	 * argument beside ev. They just raise the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(2, attacker, (p_220039_0_) -> {
			p_220039_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the
	 * "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		
		if(stack.getItem().equals(Items.AIR)) {
			return false;
		}
		
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(1, entityLiving, (p_220038_0_) -> {
				p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});

			BaseTool.changeXP(stack, 1, worldIn, entityLiving.getPosition());

			BaseTool.setLore(stack);

			List<BasicTag> tags = TagHelper.getTagList(stack);

			for (int i = 0; i < tags.size(); i++) {
				if (tags.get(i) instanceof EffectTag) {
					
					
					
					EffectTag eTag = (EffectTag) tags.get(i);
					
					if(!eTag.offensive) {
						eTag.runEffect(stack, worldIn, entityLiving);
					}


				}else if (tags.get(i) instanceof WorldInteractTag) {
					WorldInteractTag eTag = (WorldInteractTag) tags.get(i);

					eTag.runEffect(stack, worldIn, entityLiving, state, pos, null);

				}else if(tags.get(i).equals(TagHelper.UNBREAKABLE)) {
					this.setDamage(stack, 0);
				}
			}

		}

		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.field_234674_d_
				: super.getAttributeModifiers(equipmentSlot);
	}

	public float func_234675_d_() {
		return this.attackDamage;
	}



}
