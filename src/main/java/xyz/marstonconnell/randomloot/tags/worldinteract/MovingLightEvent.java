package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.entity.MovingLightTileEntity;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.utils.Registration;

public class MovingLightEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
		
		if(!worldIn.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
			pos = pos.up();
			if(!worldIn.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
				return;
			}
		}
		
		worldIn.setBlockState(pos, Registration.MOVING_LIGHT.get().getDefaultState());
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof MovingLightTileEntity) {
			MovingLightTileEntity mte = (MovingLightTileEntity) te;
			mte.init(entityLiving);
			
		}else {
			System.out.println("Error!");
		}
		
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
