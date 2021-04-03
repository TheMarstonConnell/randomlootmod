package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class FloatEvent extends WorldInteractEvent {

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, Entity target) {
		System.out.println("Making " + entityLiving.getName() + " float.");
		
		
		
		if (!entityLiving.isOnGround()) {
			if (worldIn.isRemote) {
				for (int countparticles = 0; countparticles <= 6; ++countparticles) {
					worldIn.addParticle(ParticleTypes.CLOUD, entityLiving.getPosX(), entityLiving.getPosY(),
							entityLiving.getPosZ(), 0.1D * getNegOrPos(), 0.1D, 0.1D * getNegOrPos());
				}
			}
			Vector3d vector3d = entityLiving.getMotion();
		      if (!entityLiving.isOnGround() && vector3d.y < 0.0D) {
		    	  entityLiving.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
		      }
		      
		      entityLiving.fallDistance = 0;
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

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
	}

}
