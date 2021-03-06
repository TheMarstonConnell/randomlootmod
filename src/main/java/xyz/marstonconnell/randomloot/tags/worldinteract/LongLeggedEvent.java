package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;


@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
public class LongLeggedEvent extends WorldInteractEvent{

	Map<ItemStack, Boolean> stacks = new HashMap<ItemStack, Boolean>();
	
	@SubscribeEvent
	public static void tickEvent(ServerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			
			
			
			
			
		}
		
	}
	
	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		// TODO Auto-generated method stub
		
		stacks.put(stack, true);
		entityLiving.stepHeight = 2.0f;
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		stacks.put(stack, false);
		
	}

}
