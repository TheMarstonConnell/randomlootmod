package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class BeeSummonEvent extends WorldInteractEvent{
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		if(!(target instanceof MonsterEntity)) {
			return;
		}
		
		if(target.getHealth() <= 0) {
			MobEntity liv = new BeeEntity(EntityType.BEE, worldIn);
				
				
			liv.setLocationAndAngles(target.getPosX(), target.getPosY(), target.getPosZ(), target.rotationYaw, target.rotationPitch);
			worldIn.addEntity(liv);
			liv.spawnExplosionParticle();
		}
		
		
		
		
	}
}
