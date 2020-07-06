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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class RLToolItem extends BaseTool {
	private final Set<Block> effectiveBlocks;
	protected final float efficiency;
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> field_234674_d_;

	public RLToolItem(String name, Set<Block> effectiveBlocksIn, float attackDamageIn, float attackSpeedIn) {
		super(new Properties());
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));

		this.effectiveBlocks = effectiveBlocksIn;
		this.efficiency = 8.0f;
		this.attackDamage = attackDamageIn;

		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.field_233823_f_, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.field_233825_h_, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier",
				(double) attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.field_234674_d_ = builder.build();

		// TODO Auto-generated constructor stub
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
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(1, entityLiving, (p_220038_0_) -> {
				p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});

			changeXP(stack, 1);

			setLore(stack);

			List<BasicTag> tags = TagHelper.getAllTags(stack);

			for (int i = 0; i < tags.size(); i++) {
				if (tags.get(i) instanceof EffectTag) {
					EffectTag eTag = (EffectTag) tags.get(i);

					eTag.runEffect(stack, worldIn, entityLiving);

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
