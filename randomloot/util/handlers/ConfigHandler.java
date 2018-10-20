package com.mic.randomloot.util.handlers;

import java.io.File;

import com.mic.randomloot.RandomLoot;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;
	
	public static int mobChance = 50;
	public static int playerChance = 50;
	public static int bossChance = 50;
	public static int animalChance = 50;
	public static int dropType = 0;
	public static boolean swords = true;
	public static boolean axes = true;
	public static boolean pickaxes = true;
	public static boolean shovels = true;
	public static boolean bows = true;
	
	public static void init(File file){
		
		config = new Configuration(file);
		String category;
		
		//Drop chances
		category = "Drop Chances";
		
		mobChance = config.getInt("Monster Chance", category, 50, 0, 1000, "0 for never and 1000 for every time, default is 50");
		animalChance = config.getInt("Animal Chance", category, 50, 0, 1000, "0 for never and 1000 for every time, default is 50");
		bossChance = config.getInt("Boss Chance", category, 50, 0, 1000, "0 for never and 1000 for every time, default is 50");
		playerChance = config.getInt("Player Chance", category, 50, 0, 1000, "0 for never and 1000 for every time, default is 50");
		
		//Tweaking
		category = "Tweaks";
		
		dropType = config.getInt("Drop Type", category, 0, 0, 1, "0 - any case drops any rarity (different chances), 1 - lock rarities to cases");
		swords = config.getBoolean("Swords Enabaled", category, true, "Enables or disables the item");
		axes = config.getBoolean("Axes Enabaled", category, true, "Enables or disables the item");
		bows = config.getBoolean("Bows Enabaled", category, true, "Enables or disables the item");
		pickaxes = config.getBoolean("Pickaxes Enabaled", category, true, "Enables or disables the item");
		shovels = config.getBoolean("Shovels Enabaled", category, true, "Enables or disables the item");
		
		//Damage settings
		category = "Variables";
		

		config.save();
		
	}
	
	public static void registerConfig(FMLPreInitializationEvent event){
		RandomLoot.config = new File(event.getModConfigurationDirectory() + "/" + RandomLoot.MODID);
		RandomLoot.config.mkdirs();
		init(new File(RandomLoot.config.getPath(), RandomLoot.MODID + ".cfg"));
	}
	
}
