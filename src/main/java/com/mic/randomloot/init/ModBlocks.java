package com.mic.randomloot.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.blocks.RandomAnvil;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block RANDOM_ANVIL = new RandomAnvil("random_anvil", RandomLoot.randomlootTab);
	
	@Mod.EventBusSubscriber(modid = RandomLoot.MODID)
	public static class BlockRegistry
	{
		public static final Set<Block> BLOCK_SET = new HashSet<Block>();
		@SubscribeEvent
		public static void newRegistry(final RegistryEvent.NewRegistry event)
		{
			;
		}
		@SubscribeEvent
		public static void register(final RegistryEvent.Register<Block> event)
		{
			final IForgeRegistry<Block> registry = event.getRegistry();
			
			for (final Block block : BLOCKS)
			{
				registry.register(block);
				BLOCK_SET.add(block);
			}
			
		}
	}
}
