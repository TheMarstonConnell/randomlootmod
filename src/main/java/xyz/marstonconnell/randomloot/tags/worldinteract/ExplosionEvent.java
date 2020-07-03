package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class ExplosionEvent extends WorldInteractEvent{

	@Override
	public void effect(ItemStack stack, World worldIn,  LivingEntity entityLiving, BlockState state, BlockPos pos) {
		if (!worldIn.isRemote) {
			float f = 4.0F;
			worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true, Mode.BREAK);

		}		
	}

	

}
