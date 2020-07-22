package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class DamageEvent extends WorldInteractEvent {
	float damage = 0.18f;

	public DamageEvent(float damage) {
		this.damage = damage;
	}

	@Override
	public void effect(ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, LivingEntity target) {

		float currentHP = target.getHealth();
		target.attackEntityFrom(DamageSource.MAGIC, currentHP * this.damage);

	}

}
