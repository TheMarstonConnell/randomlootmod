package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class TeleportItemsEvent extends WorldInteractEvent{

	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, Entity target) {
		final int radius = 1 + level;
		
		List<ItemEntity> entities = worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius));
		for(ItemEntity item : entities) {
			item.setPositionAndUpdate(entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ());
		}		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
