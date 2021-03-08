package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import xyz.marstonconnell.randomloot.utils.Pair;


@Mod.EventBusSubscriber(modid = RandomLootMod.MODID)
public class LongLeggedEvent extends WorldInteractEvent{

	static Map<ItemStack, Pair<LivingEntity, Boolean>> stacks = new HashMap<ItemStack, Pair<LivingEntity, Boolean>>();
	
	@SubscribeEvent
	public static void tickEvent(ServerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			Set<ItemStack> keys  = stacks.keySet();
			
			for(ItemStack s : keys) {
				Pair<LivingEntity, Boolean> p = stacks.get(s);
				if(p.getRight()) {
					p.setRight(false);
					
					p.getLeft().stepHeight = 0.5f;
				}
				
			}
			
			
			
		}
		
	}
	
	
	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		// TODO Auto-generated method stub
		
		stacks.put(stack, new Pair<LivingEntity, Boolean>(entityLiving, true));
		entityLiving.stepHeight = 0.5f * level + 0.5f;
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, LivingEntity target) {
		
		stacks.put(stack, new Pair<LivingEntity, Boolean>(entityLiving, false));
		
	}

}
