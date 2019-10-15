package com.mic.randomloot.tags.worldinteract;

import com.mic.randomloot.tags.WorldInteractEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExplosionEvent extends WorldInteractEvent{

	@Override
	public void effect(ItemStack stack, World worldIn, EntityLivingBase entityLiving, IBlockState state, BlockPos pos) {
		if (!worldIn.isRemote) {
			float f = 4.0F;
			worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);

		}		
	}

	

}
