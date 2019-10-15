package com.mic.randomloot.tags.worldinteract;

import java.util.Random;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.tags.WorldInteractEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReplenishEvent extends WorldInteractEvent {

	@Override
	public void effect(ItemStack stack, World worldIn, EntityLivingBase entityLiving, IBlockState state, BlockPos pos) {

		switch (RandomLoot.rand.nextInt(6)) {
		case 4:
		case 5:
			((EntityPlayer) entityLiving).getFoodStats().addStats((ItemFood) Items.BEETROOT,
					new ItemStack(Items.BEETROOT));
		}
	}

}
