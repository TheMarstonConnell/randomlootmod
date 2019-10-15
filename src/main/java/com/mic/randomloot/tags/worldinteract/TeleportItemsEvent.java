package com.mic.randomloot.tags.worldinteract;

import java.util.List;

import com.mic.randomloot.tags.WorldInteractEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TeleportItemsEvent extends WorldInteractEvent{

	@Override
	public void effect(ItemStack stack, World worldIn, EntityLivingBase entityLiving, IBlockState state, BlockPos pos) {
		List<EntityItem> entities = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		for(EntityItem item : entities) {
			item.setPositionAndUpdate(entityLiving.posX, entityLiving.posY, entityLiving.posZ);
		}		
	}

}
