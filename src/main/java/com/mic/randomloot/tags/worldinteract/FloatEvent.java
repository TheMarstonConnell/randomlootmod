package com.mic.randomloot.tags.worldinteract;

import java.util.Random;

import com.mic.randomloot.tags.WorldInteractEvent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FloatEvent extends WorldInteractEvent {

	@Override
	public void effect(ItemStack stack, World worldIn, EntityLivingBase entityLiving, IBlockState state, BlockPos pos) {
		System.out.println("Making " + entityLiving.getName() + " float.");
		if (!entityLiving.onGround) {
			if (worldIn.isRemote) {
				for (int countparticles = 0; countparticles <= 6; ++countparticles) {
					worldIn.spawnParticle(EnumParticleTypes.CLOUD, entityLiving.posX, entityLiving.posY,
							entityLiving.posZ, 0.1D * getNegOrPos(), 0.1D, 0.1D * getNegOrPos());
				}
			}
		}
	}

	public int getNegOrPos() {
		Random rand = new Random();

		switch (rand.nextInt(3)) {
		case 0:
			return -1;
		case 1:
			return 1;
		case 2:
			return 0;
		}
		return 1;

	}

}
