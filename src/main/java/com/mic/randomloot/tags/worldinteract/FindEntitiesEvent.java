package com.mic.randomloot.tags.worldinteract;

import java.util.List;

import com.mic.randomloot.tags.WorldInteractEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FindEntitiesEvent extends WorldInteractEvent {
	int radius = 10;

	@Override
	public void effect(ItemStack stack, World worldIn, EntityLivingBase entityLiving, IBlockState state, BlockPos pos) {
		List<EntityLiving> mobs = worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX() + radius, pos.getY() + radius,
				pos.getZ() + radius, pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius));

		for(EntityLiving mob : mobs) {
			mob.setGlowing(true);
		}
		
	}

}
