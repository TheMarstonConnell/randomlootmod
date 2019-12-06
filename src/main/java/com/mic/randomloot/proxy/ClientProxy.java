package com.mic.randomloot.proxy;

import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.handlers.ConfigHandler;
import com.mic.randomloot.util.handlers.RenderHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientProxy extends CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@SubscribeEvent
	public void onPlayerJoin(TickEvent.PlayerTickEvent event) {
		// System.out.println("Player Joined, sending wlecome message.");
		if (event.player.world.isRemote && event.player == FMLClientHandler.instance().getClientPlayerEntity()) {
			MinecraftForge.EVENT_BUS.unregister(this);
			if (ConfigHandler.doWelcomeMessage) {
				event.player.sendMessage(new TextComponentString(TextFormatting.GOLD
						+ "Thank you for installing Random Loot by milomaz1, follow me on twitter @MarstonConnell for updates!"));
				event.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 2.5F, 1.0F);
			}
		}

	}

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		// ModItems.registerModels();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
//		System.out.println("PreInit Success");
		// ModItems.registerModels();
		
		RenderHandler.registerEntityRenders();


	}

}
