package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class LowDurabilityAttackEvent extends WorldInteractEvent {

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity t) {
		if(!(t instanceof LivingEntity)) {
			return;
		}
		
		LivingEntity target = (LivingEntity) t;
		
		
		float current = stack.getDamage();
		float max = stack.getMaxDamage();
		
		float toAdd = current / max * level * 2;
		
		target.hurtResistantTime = 0;
		target.hurtTime = 0;
		target.attackEntityFrom(new IndirectEntityDamageSource("indirectMagic", entityLiving, null).setDamageBypassesArmor().setMagicDamage(), toAdd);

		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
