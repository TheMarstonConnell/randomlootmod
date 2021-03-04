package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class GangBangEvent extends WorldInteractEvent {

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		// TODO Auto-generated method stub

		int dist = 8 * level;

		int extraDamage = 0;
		List<LivingEntity> mobs = worldIn.getEntitiesWithinAABB(target.getClass(),
				new AxisAlignedBB(dist, -dist / 4, dist, -dist, dist / 4, -dist));
		for (LivingEntity ent : mobs) {
			if (target.getClass() == ent.getClass()) {
				extraDamage++;
			}
		}

		target.hurtResistantTime = 0;
		target.hurtTime = 0;
		target.attackEntityFrom(new IndirectEntityDamageSource("indirectMagic", entityLiving, null)
				.setDamageBypassesArmor().setMagicDamage(), extraDamage);

	}

}
