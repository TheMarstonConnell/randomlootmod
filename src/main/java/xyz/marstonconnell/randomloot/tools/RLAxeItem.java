package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.EffectTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tags.WorldInteractTag;
import xyz.marstonconnell.randomloot.utils.Config;

public class RLAxeItem extends RLToolItem implements IRLTool {

	
	
	 private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);
	   private static final Set<Block> EFFECTIVE_ON_BLOCKS = Sets.newHashSet(Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON);
	   protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).build();

	public RLAxeItem(String name) {
		super(name, EFFECTIVE_ON_BLOCKS, 5.0f, -3.0f);
		
		
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack) {
		HashSet<ToolType> hs = new HashSet<ToolType>();
		hs.add(ToolType.AXE);
		return hs;
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context) {
		
		
		World world = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockState blockstate = world.getBlockState(blockpos);
		Block block = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());
		if (block != null) {
			PlayerEntity playerentity = context.getPlayer();
			world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote) {
				world.setBlockState(blockpos,
						block.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)),
						11);
				if (playerentity != null) {
					context.getItem().damageItem(1, playerentity, (p_220040_1_) -> {
						p_220040_1_.sendBreakAnimation(context.getHand());
					});
				}
			}

			return ActionResultType.func_233537_a_(world.isRemote);
		} else {
			return ActionResultType.PASS;
		}
	}

	public String getItemType() {
		return "axe";
	}

	public boolean canHarvestBlock(BlockState blockIn) {
		int i = 4;
		if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.AXE) {
			return i >= blockIn.getHarvestLevel();
		}
		Material material = blockIn.getMaterial();
		return EFFECTIVE_ON_MATERIALS.contains(material);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		float speedBonus = nbt.getFloat("rl_dig_speed");

		Material material = state.getMaterial();
		return EFFECTIVE_ON_MATERIALS.contains(material) ? this.efficiency + speedBonus - 1
				: super.getDestroySpeed(stack, state);
	}
	
	/**
	 * Current implementations of this method in child classes do not use the entry
	 * argument beside ev. They just raise the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (livingEntity) -> {
			livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		
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

		}
		}
		
		
		return true;
	}

	@Override
	public List<BasicTag> getAllowedTags() {
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (eTag.forTools || eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			} else if (tag instanceof WorldInteractTag) {
				WorldInteractTag eTag = (WorldInteractTag) tag;
				if (eTag.forTools || eTag.forWeapons) {
					allowedTags.add(eTag);
				}
			}

		}

		allowedTags.add(TagHelper.UNBREAKABLE);
		allowedTags.add(TagHelper.REPLENISH);

		return allowedTags;
	}

	@Override
	public void setStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		int dmg = Config.BASE_AXE_DAMAGE.get();
		double spd = Config.BASE_AXE_ATTACK_SPEED.get();

		nbt.putInt("rl_damage", dmg);
		nbt.putDouble("rl_speed", spd);
		nbt.putFloat("rl_dig_speed", 1);
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

		IntArrayNBT UUID = new IntArrayNBT(new int[] { 1, 2, 3, 4 });

		damage.put("UUID", UUID);
		damage.put("Slot", StringNBT.valueOf("mainhand"));

		// speed
		CompoundNBT speed = new CompoundNBT();
		speed.put("AttributeName", StringNBT.valueOf("generic.attack_speed"));
		speed.put("Name", StringNBT.valueOf("generic.attack_speed"));

		speed.put("Amount", DoubleNBT.valueOf(spd));
		speed.put("Operation", IntNBT.valueOf(0));

		UUID = new IntArrayNBT(new int[] { 5, 6, 7, 8 });

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
		nbt.putFloat("rl_dig_speed", nbt.getFloat("rl_dig_speed") * (1.1f));

		stack.setTag(nbt);

		updateStats(stack);

		BaseTool.setLore(stack);

	}


	@Override
	public List<String> getStatsLore(ItemStack stack) {
		DecimalFormat f = new DecimalFormat("#0.00");

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		List<String> s = new ArrayList<String>();
		s.add(TextFormatting.GRAY + "Attack Damage: " + nbt.getInt("rl_damage"));
		s.add(TextFormatting.GRAY + "Attack Speed: " + f.format(4 + nbt.getDouble("rl_speed")));

		s.add(TextFormatting.GRAY + "Dig Speed: " + f.format(this.efficiency - 1 + nbt.getFloat("rl_dig_speed")));

		return s;
	}

	@Override
	public int getVariants() {
		// TODO Auto-generated method stub
		return 11;
	}
}
