package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.utils.Registration;

public class PlaceLightEvent extends WorldInteractEvent {

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {

		if(!(entityLiving instanceof PlayerEntity)) {
			return;
		}
		
		PlayerEntity player = (PlayerEntity) entityLiving;
		
		RayTraceResult trace = calcRayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);

		if (trace.getType() == RayTraceResult.Type.BLOCK) {
			BlockRayTraceResult blockTrace = (BlockRayTraceResult) trace;
			Direction face = blockTrace.getFace();

			BlockPos p = blockTrace.getPos();
			switch (face) {
			case DOWN:
				p = p.add(0, -1, 0);
				break;
			case UP:
				p = p.add(0, 1, 0);
				break;
			case WEST:
				p = p.add(-1, 0, 0);
				break;
			case EAST:
				p = p.add(1, 0, 0);
				break;
			case NORTH:
				p = p.add(0, 0, -1);
				break;
			case SOUTH:
				p = p.add(0, 0, 1);
				break;
			default:
				break;
			}
			
			
			worldIn.setBlockState(p, Registration.LIGHT_BALL.get().getDefaultState());
			stack.setDamage(stack.getDamage() + 4 + (int)(Math.random() * 3));
			
		}

	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub

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

}
