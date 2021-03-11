package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;
import xyz.marstonconnell.randomloot.tools.BaseTool;

public class BurningEvent extends WorldInteractEvent{

	int ticks = 160;
	int size = 8;
	int time = 3;
	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
		float f = BaseTool.getFloatNBT(stack, "rl_burn_timer");
		
		if(f < ticks) {
			BaseTool.setFloatNBT(stack, "rl_burn_timer", f + 1);
			return;
		}
		
		
		BaseTool.setFloatNBT(stack, "rl_burn_timer", 0.0f);

		
		List<MonsterEntity> mobs = worldIn.getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB(pos.getX() - size, pos.getY() - size, pos.getZ() - size, pos.getX() + size, pos.getY() + size, pos.getZ() + size));
		
		for(MonsterEntity m : mobs) {
			m.setFire(time * level);
		}
		

		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}
	
}
