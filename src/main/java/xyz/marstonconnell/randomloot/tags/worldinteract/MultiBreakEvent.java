package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class MultiBreakEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		worldIn.setBlockState(pos, state);
		
		RayTraceContext rtc = new RayTraceContext(entityLiving.getLookVec(), new Vector3d(pos.getX(), pos.getY(), pos.getZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entityLiving);
		
		BlockRayTraceResult face = worldIn.rayTraceBlocks(rtc);
		
		System.out.println(face.getType() + " -- " + face.getFace() + " -- " + face.getPos());
		
		
		//stack.getItem().canHarvestBlock(blockIn)
		
		
		
	}

}
