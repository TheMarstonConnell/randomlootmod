package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RLSwordItem extends SwordItem implements IRLTool{
	private final float attackDamage;

	
	public int getVariants() {
		return 35;
	}
	
	@Override
	public List<String> getStatsLore(ItemStack stack){
		DecimalFormat f = new DecimalFormat("#0.00");
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		List<String> s = new ArrayList<String>();
		s.add(TextFormatting.GRAY + "Attack Damage: " + nbt.getInt("rl_damage"));
		s.add(TextFormatting.GRAY + "Attack Speed: "
				+ f.format(4 + nbt.getDouble("rl_speed")));
		
		return s;
		
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

	private final Multimap<Attribute, AttributeModifier> attributes;

	public RLSwordItem(String name, int attackDamageIn, float attackSpeedIn) {
		super(ItemTier.DIAMOND, attackDamageIn, attackSpeedIn, new Properties());
		attackDamage = attackDamageIn;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));

		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",
				(double) attackSpeedIn, AttributeModifier.Operation.ADDITION));

		this.attributes = builder.build();

		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));
		
		if(FMLEnvironment.dist == Dist.CLIENT) {
            TextureProxy.setModelProperties(this);
        }
		RLItems.ITEMS.add(this);
	}

	public String getItemType() {
		return "sword";
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.isIn(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL
					&& !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Current implementations of this method in child classes do not use the entry
	 * argument beside ev. They just raise the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (livingEntity) -> {
			livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		
		if(stack.getItem().equals(Items.AIR)) {
			return false;
		}
		
		BaseTool.changeXP(stack, 1, attacker.getEntityWorld(), attacker.getPosition());
		
		BaseTool.setLore(stack);
		
		List<BasicTag> tags = TagHelper.getTagList(stack);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				if (eTag.offensive) {

					eTag.runEffect(stack, attacker.world, target);
				} else {

					eTag.runEffect(stack, attacker.world, attacker);

				}
			}else if (tags.get(i) instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tags.get(i);

					eTag.runEffect(stack, attacker.world, attacker, attacker.getEntityWorld().getBlockState(new BlockPos(attacker.getPositionVec())), new BlockPos(attacker.getPositionVec()), target);

			}else if(tags.get(i).equals(TagHelper.UNBREAKABLE)) {
				this.setDamage(stack, 0);
			}
		}
		
		
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the
	 * "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(2, entityLiving, (attacker) -> {
				attacker.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}

		return true;
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	public boolean canHarvestBlock(BlockState blockIn) {
		return blockIn.isIn(Blocks.COBWEB);
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributes
				: super.getAttributeModifiers(equipmentSlot);
	}

	@Override
	public void setStats(ItemStack stack) {

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		int dmg = Config.BASE_SWORD_DAMAGE.get();
		double spd = Config.BASE_SWORD_SPEED.get();

		
		nbt.putInt("rl_damage", dmg);
		nbt.putDouble("rl_speed", spd);
		BaseTool.setIntNBT(stack, "rl_level", 1);
		
		stack.setTag(nbt);

	}

	@Override
	public void updateStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		CompoundNBT damage = new CompoundNBT();
		damage.put("AttributeName", StringNBT.valueOf("generic.attack_damage"));
		damage.put("Name", StringNBT.valueOf("generic.attack_damage"));
		
		int dmg = nbt.getInt("rl_damage");
		double spd = nbt.getDouble("rl_speed");

		
		damage.put("Amount", IntNBT.valueOf(dmg));
		damage.put("Operation", IntNBT.valueOf(0));
		
		IntArrayNBT UUID = new IntArrayNBT(new int[] {1,2,3,4});
		
		damage.put("UUID", UUID);
		damage.put("Slot", StringNBT.valueOf("mainhand"));

		// speed
		CompoundNBT speed = new CompoundNBT();
		speed.put("AttributeName", StringNBT.valueOf("generic.attack_speed"));
		speed.put("Name", StringNBT.valueOf("generic.attack_speed"));

		
		speed.put("Amount", DoubleNBT.valueOf(spd));
		speed.put("Operation", IntNBT.valueOf(0));

		UUID = new IntArrayNBT(new int[] {5,6,7,8});
		
		speed.put("UUID", UUID);
		
		speed.put("Slot", StringNBT.valueOf("mainhand"));
		

		ListNBT modifiers = new ListNBT();

		modifiers.add(damage);
		modifiers.add(speed);
		
		

		nbt.put("AttributeModifiers", modifiers);
		
		stack.setTag(nbt);
	}

	@Override
	public void upgradeTool(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		

		
		nbt.putInt("rl_damage", nbt.getInt("rl_damage") + 1);
		nbt.putDouble("rl_speed", nbt.getDouble("rl_speed") * (0.9));
		
		stack.setTag(nbt);
		
		updateStats(stack);
		
	}

}
