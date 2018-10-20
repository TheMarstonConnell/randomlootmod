package com.mic.randomloot;

import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.mic.randomloot.proxy.CommonProxy;
import com.mic.randomloot.util.ModTab;
import com.mic.randomloot.util.handlers.RegistryHandler;

@Mod(modid = RandomLoot.MODID, name = RandomLoot.NAME, version = RandomLoot.VERSION)
public class RandomLoot {

	public static File config;

	public static final String MODID = "randomloot";
	public static final String NAME = "Random Loot Mod";
	public static final String VERSION = "1.3";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String CLIENT_PROXY_CLASS = "com.mic.randomloot.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.mic.randomloot.proxy.CommonProxy";

	static RegistryHandler eventHandler;
	
	@Mod.Instance(RandomLoot.MODID)
	public static RandomLoot instance;

	@SidedProxy(clientSide = RandomLoot.CLIENT_PROXY_CLASS, serverSide = RandomLoot.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final ModTab randomlootTab = new ModTab();

	private static Logger logger;

	public RandomLoot() {

		Launch.classLoader.addTransformerExclusion("org.apache.commons.lang3");
	}

	// Event Handlers
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {

		eventHandler = new RegistryHandler();

		MinecraftForge.EVENT_BUS.register(eventHandler);

		logger = event.getModLog();
		eventHandler.preInitRegistries(event);
		proxy.preInit(event);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

		eventHandler.initRegistries();
		proxy.init(event);
	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {

	}
}
