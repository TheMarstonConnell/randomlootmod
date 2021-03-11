package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class MultiShotEvent extends WorldInteractEvent{

	private ArrowEntity cloneArrow(ArrowEntity arrow) {
		ArrowEntity newArrow = new ArrowEntity(EntityType.ARROW, arrow.getEntityWorld());
		newArrow.setIsCritical(arrow.getIsCritical());
		newArrow.setLocationAndAngles(arrow.getPosX(), arrow.getPosY(), arrow.getPosZ(), arrow.rotationYaw, arrow.rotationPitch);
		newArrow.setMotion(arrow.getMotion());
		
		return newArrow;

	}
	
	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
		if(!(target instanceof AbstractArrowEntity)) {
			return;
		}
		
		System.out.println("MULTISHOT");
		
		ArrowEntity arrow = (ArrowEntity) target;
		
		
		if(!(entityLiving instanceof PlayerEntity)) {
			return;
		}
		
		PlayerEntity player = (PlayerEntity) entityLiving;
		

		if(level == 1) {	
			ArrowEntity newArrow = cloneArrow(arrow);
			newArrow.setMotion(newArrow.getMotion().rotateYaw(-0.05f));
			arrow.setMotion(arrow.getMotion().rotateYaw(0.05f));
			worldIn.addEntity(newArrow);
			
		}else if(level == 2) {
			ArrowEntity newArrow = cloneArrow(arrow);
			newArrow.setMotion(newArrow.getMotion().rotateYaw(-0.1f));
			worldIn.addEntity(newArrow);
			
			ArrowEntity newArrow2 = cloneArrow(arrow);
			newArrow2.setMotion(newArrow2.getMotion().rotateYaw(0.1f));
			worldIn.addEntity(newArrow2);
		}
		
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
	}

}
