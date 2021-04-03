package xyz.marstonconnell.randomloot.tags.worldinteract;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class EasterEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
		Entity e = null;
		
		switch ((int) (Math.random() * 6)) {
		case 0:
			ItemEntity i = new ItemEntity(EntityType.ITEM, worldIn);
			i.setItem(new ItemStack(Items.EGG));
			e = i;
			break;
		case 1:
			RabbitEntity r = new RabbitEntity(EntityType.RABBIT, worldIn);
			r.setCustomName(new StringTextComponent("Funny Bunny"));
			e= r;
			break;
		case 2:
			ChickenEntity c = new ChickenEntity(EntityType.CHICKEN, worldIn);
			c.setCustomName(new StringTextComponent("Clucks McCluckin'"));
			e = c;
			break;
		default:
			return;
		}
		
		
		e.setPosition(pos.getX(), pos.getY(), pos.getZ());
		
		worldIn.addEntity(e);
		
		
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
