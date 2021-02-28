package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class ReplenishEvent extends WorldInteractEvent {

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos, LivingEntity target) {

		switch (RandomLootMod.rand.nextInt(6)) {
		case 4:
		case 5:
			((PlayerEntity) entityLiving).getFoodStats().addStats(level, 1);
			break;
		}
	}

}
