package xyz.marstonconnell.randomloot.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.Direction;
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

public class RLShovelItem extends RLToolItem implements IRLTool{
	
	   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
	   /** Map used to lookup shovel right click interactions */
	   protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

	
	public RLShovelItem(String name) {
		super(name, EFFECTIVE_ON, 1.5F, -3.0F);
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack) {
		HashSet<ToolType> hs = new HashSet<ToolType>();
		hs.add(ToolType.SHOVEL);
		return hs;
	}
	
	/**
	    * Called when this item is used when targetting a Block
	    */
	   public ActionResultType onItemUse(ItemUseContext context) {
	      World world = context.getWorld();
	      BlockPos blockpos = context.getPos();
	      BlockState blockstate = world.getBlockState(blockpos);
	      if (context.getFace() == Direction.DOWN) {
	         return ActionResultType.PASS;
	      } else {
	         PlayerEntity playerentity = context.getPlayer();
	         BlockState blockstate1 = SHOVEL_LOOKUP.get(blockstate.getBlock());
	         BlockState blockstate2 = null;
	         if (blockstate1 != null && world.isAirBlock(blockpos.up())) {
	            world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
	            blockstate2 = blockstate1;
	         } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
	            if (!world.isRemote()) {
	               world.playEvent((PlayerEntity)null, 1009, blockpos, 0);
	            }

	            CampfireBlock.extinguish(world, blockpos, blockstate);
	            blockstate2 = blockstate.with(CampfireBlock.LIT, Boolean.valueOf(false));
	         }

	         if (blockstate2 != null) {
	            if (!world.isRemote) {
	               world.setBlockState(blockpos, blockstate2, 11);
	               if (playerentity != null) {
	                  context.getItem().damageItem(1, playerentity, (p_220041_1_) -> {
	                     p_220041_1_.sendBreakAnimation(context.getHand());
	                  });
	               }
	            }

	            return ActionResultType.func_233537_a_(world.isRemote);
	         } else {
	            return ActionResultType.PASS;
	         }
	      }
	   }
	
	public String getItemType() {
		return "pickaxe";
	}

	public boolean canHarvestBlock(BlockState blockIn) {
	      return blockIn.isIn(Blocks.SNOW) || blockIn.isIn(Blocks.SNOW_BLOCK);

	}

	public float getDestroySpeed(ItemStack stack, BlockState state) {
		
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		float speedBonus = nbt.getFloat("rl_dig_speed");
		
		Material material = state.getMaterial();
		return material != Material.SNOW && material != Material.SNOW_BLOCK && material != Material.CLAY && material != Material.EARTH && material != Material.SAND
				? super.getDestroySpeed(stack, state)
				: this.efficiency + speedBonus - 1;
	}
	
	@Override
	public List<BasicTag> getAllowedTags() {
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (eTag.forTools) {
					allowedTags.add(eTag);
				}
			} else if (tag instanceof WorldInteractTag) {
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

	@Override
	public void setStats(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		int dmg = Config.BASE_SPADE_DAMAGE.get();
		double spd = Config.BASE_SPADE_ATTACK_SPEED.get();

		 
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
		

		
		nbt.putDouble("rl_speed", nbt.getDouble("rl_speed") * (0.9));
		nbt.putFloat("rl_dig_speed", nbt.getFloat("rl_dig_speed") * (1.1f));
		
		
		stack.setTag(nbt);
		
		updateStats(stack);
		
		BaseTool.setLore(stack);

	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
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
		s.add(TextFormatting.GRAY + "Attack Speed: "
				+ f.format(4 + nbt.getDouble("rl_speed")));
		
		s.add(TextFormatting.GRAY + "Dig Speed: "
				+ f.format(this.efficiency - 1 + nbt.getFloat("rl_dig_speed")));
		
		return s;
	}

	@Override
	public int getVariants() {
		// TODO Auto-generated method stub
		return 8;
	}
}
