package xyz.marstonconnell.randomloot.tags.StatBoost;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.StatBoostEvent;
import xyz.marstonconnell.randomloot.tools.ToolUtilities;

public class LowDurabilityBoostEvent extends StatBoostEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockPos pos,
			LivingEntity target) {

		
		float current = stack.getDamage();
		float max = stack.getMaxDamage();
		
		float toAdd = current / max * level * 2;
		
		ToolUtilities.setFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED, ToolUtilities.getFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED) + toAdd);

		
		
	}

	@Override
	public void undoEffect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockPos pos,
			LivingEntity target) {
		// TODO Auto-generated method stub
		ToolUtilities.setFloatNBT(stack, ToolUtilities.TAG_BONUS_SPEED, 0.0f);

	}

}
