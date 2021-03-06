package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.tools.RLToolItem;

public class MultiBreakEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity targetEntity) {
		
		if(!(entityLiving instanceof PlayerEntity)) {
			return;
		}
		
		if(!(stack.getItem() instanceof RLToolItem)) {
			return;
		}
		
		RLToolItem item = (RLToolItem) stack.getItem();
		
		PlayerEntity player = (PlayerEntity) entityLiving;
		
		worldIn.setBlockState(pos, state);
		
		
        RayTraceResult trace = calcRayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
		
        if (trace.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
            Direction face = blockTrace.getFace();

            for (int a = -level; a <= level; a++) {
                for (int b = -level; b <= level; b++) {
                    if (a == 0 && b == 0) continue;

                    BlockPos target = null;

                    if (face == Direction.UP    || face == Direction.DOWN)  target = pos.add(a, 0, b);
                    if (face == Direction.NORTH || face == Direction.SOUTH) target = pos.add(a, b, 0);
                    if (face == Direction.EAST  || face == Direction.WEST)  target = pos.add(0, a, b);

                    
                    
                    attemptBreak(worldIn, target, pos, player, item.getEffectiveMaterials(), item.getEffectiveOn(), true);
                }
            }
        }
		
		//stack.getItem().canHarvestBlock(blockIn)
		
		
		
	}
	
	/** Attempt to break a block. Fails if the tool is not effective on the given block, or if the origin broken block
     * is significantly easier to break than the target block (i.e. don't let players use sandstone to quickly area
     * break obsidian).*/
    public static void attemptBreak(World world,
                                    BlockPos target,
                                    BlockPos origin,
                                    PlayerEntity player,
                                    Set<Material> effectiveMaterials,
                                    Set<Block> effectiveOn,
                                    boolean checkHarvestLevel) {

        BlockState state = world.getBlockState(target);

        if (checkHarvestLevel && !ForgeHooks.canHarvestBlock(state, player, world, target)) {
            return; // We are checking harvest level and this tool doesn't qualify.
        }

        if (!effectiveMaterials.contains(state.getMaterial()) && !effectiveOn.contains(state.getBlock())) {
            return; // This tool is not effective on this block.
        }

        // Prevent players from using low-hardness blocks to quickly break adjacent high-hardness blocks.
        if (origin != null) {
            BlockState originState = world.getBlockState(origin);
            float originHard = originState.getPlayerRelativeBlockHardness(player, world, origin);
            float targetHard = state.getPlayerRelativeBlockHardness(player, world, target);
            if (originHard / targetHard > 10f) {
                return;
            }
        }

        ServerPlayerEntity spe = (ServerPlayerEntity) player;
        int expDropped = ForgeHooks.onBlockBreakEvent(world, spe.interactionManager.getGameType(), spe, target);

        Block block = state.getBlock();
        if (block.removedByPlayer(state, world, target, player, true, world.getFluidState(target))) {
            block.onPlayerDestroy(world, target, state);
            block.harvestBlock(world, player, target, state, world.getTileEntity(target), player.getHeldItemMainhand());
            block.dropXpOnBlockBreak((ServerWorld) world, target, expDropped);
        }
    }

    /** Attempt to break a block. Fails if the tool is not effective on the given block.*/
    public static void attemptBreak(World world,
                                    BlockPos target,
                                    PlayerEntity player,
                                    Set<Material> effectiveMaterials,
                                    Set<Block> effectiveOn,
                                    boolean checkHarvestLevel) {
        attemptBreak(world, target, null, player, effectiveMaterials, effectiveOn, checkHarvestLevel);
    }
	
	/** Copy-pasted from "Item.rayTrace" which is protected static, making it unusable in my own static helper methods.*/
    public static RayTraceResult calcRayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vector3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();;
        Vector3d vector3d1 = vector3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

}
