package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class InstaKillEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		if(target.getHealth() / target.getMaxHealth() < 0.05 * level) {
			target.setHealth(0);
		}
		
	}
	
}
