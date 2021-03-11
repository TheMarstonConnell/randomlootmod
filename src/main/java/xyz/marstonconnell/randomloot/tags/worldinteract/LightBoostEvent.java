package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class LightBoostEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity t) {
		// TODO Auto-generated method stub
		
		if(!(t instanceof LivingEntity)) {
			return;
		}
		
		LivingEntity target = (LivingEntity) t;
		
		target.hurtResistantTime = 0;
		target.hurtTime = 0;
		float light = worldIn.getLightValue(entityLiving.getPosition());
		
		if(stack.getToolTypes().isEmpty()) {
			target.attackEntityFrom(new IndirectEntityDamageSource("indirectMagic", entityLiving, null).setDamageBypassesArmor().setMagicDamage(), (float) Math.ceil(light / 2.0f));
			return;
		}
		
		stack.setDamage((int) Math.min(stack.getDamage() - Math.ceil(light / 3.0f), stack.getMaxDamage()));
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
