package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class DamageEvent extends WorldInteractEvent {
	float damage = 0.18f;


	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, Entity t) {
		if(t == null) {
			return;
		}
		
		if(!(t instanceof LivingEntity)) {
			return;
		}
		
		LivingEntity target = (LivingEntity) t;
		
		float currentHP = target.getHealth();
		target.hurtResistantTime = 0;
		target.hurtTime = 0;
		target.attackEntityFrom(new IndirectEntityDamageSource("indirectMagic", entityLiving, null).setDamageBypassesArmor().setMagicDamage(), currentHP * this.damage * level);

	}


	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
