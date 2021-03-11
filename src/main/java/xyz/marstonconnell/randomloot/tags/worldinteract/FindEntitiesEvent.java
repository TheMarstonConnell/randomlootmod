package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class FindEntitiesEvent extends WorldInteractEvent {
	int radius = 10;

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, Entity target) {
		List<LivingEntity> mobs = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() + radius, pos.getY() + radius,
				pos.getZ() + radius, pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius));

		for(LivingEntity mob : mobs) {
			if(!mob.equals(entityLiving)) {
				mob.addPotionEffect(new EffectInstance(Effects.GLOWING, 100));
			}
		}
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
