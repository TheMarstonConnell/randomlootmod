package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class TeleportItemsEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, LivingEntity target) {
		List<ItemEntity> entities = worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		for(ItemEntity item : entities) {
			item.setPositionAndUpdate(entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ());
		}		
	}

}
