package xyz.marstonconnell.randomloot.tags;

import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class StatBoostEvent {
	public abstract void effect(int level, ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockPos pos, @Nullable LivingEntity target);
	
	public abstract void undoEffect(int level, ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockPos pos, @Nullable LivingEntity target);
}
