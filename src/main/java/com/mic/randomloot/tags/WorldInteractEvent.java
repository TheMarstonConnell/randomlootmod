package com.mic.randomloot.tags;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldInteractEvent {
	public abstract void effect(ItemStack stack, World worldIn,
			EntityLivingBase entityLiving, IBlockState state, BlockPos pos);
}
