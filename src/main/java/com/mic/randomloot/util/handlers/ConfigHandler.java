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
	
	public static int tierOneDamageMin = 7;
	public static int tierOneDamageMax = 11;
	public static int tierTwoDamageMin = 10;
	public static int tierTwoDamageMax = 16;
	public static int tierThreeDamageMin = 15;
	public static int tierThreeDamageMax = 22;
	public static boolean unbreakable = true;
	public static boolean dropFromMobs = true;
	public static boolean spawnInChests = true;
	public static int chestSpawnRate = 100;
	
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
		
		dropFromMobs = config.getBoolean("Drop from Mobs", category, true, "Enables or disables cases dropping from mobs");
		spawnInChests = config.getBoolean("Spawn in chests", category, true, "Enables or disables cases spawining in chests");
		chestSpawnRate = config.getInt("Chest Spawn Rate", category, 50, 0, 100, "If chest spawning is enabled, change the chance to spawn");
		dropType = config.getInt("Drop Type", category, 0, 0, 1, "0 - any case drops any rarity (different chances), 1 - lock rarities to cases");
		swords = config.getBoolean("Swords Enabled", category, true, "Enables or disables the item");
		axes = config.getBoolean("Axes Enabled", category, true, "Enables or disables the item");
		bows = config.getBoolean("Bows Enabled", category, true, "Enables or disables the item");
		pickaxes = config.getBoolean("Pickaxes Enabled", category, true, "Enables or disables the item");
		shovels = config.getBoolean("Shovels Enabled", category, true, "Enables or disables the item");
		
		//Damage settings
		category = "Variables";
		tierOneDamageMin = config.getInt("Tier One Minimum Damage", category, 7, 0, 99, "Sets the minimum damage of the weapons.");
		tierOneDamageMax = config.getInt("Tier One Maximum Damage", category, 11, 0, 100, "Sets the maximum damage of the weapons.");
		tierTwoDamageMin = config.getInt("Tier Two Minimum Damage", category, 10, 0, 99, "Sets the minimum damage of the weapons.");
		tierTwoDamageMax = config.getInt("Tier Two Maximum Damage", category, 16, 0, 100, "Sets the maximum damage of the weapons.");
		tierThreeDamageMin = config.getInt("Tier Three Minimum Damage", category, 15, 0, 99, "Sets the minimum damage of the weapons.");
		tierThreeDamageMax = config.getInt("Tier Three Maximum Damage", category, 22, 0, 100, "Sets the maximum damage of the weapons.");
		unbreakable = config.getBoolean("Unbreakable Tools Allowed", category, true, "False turns off the 'unbreakable' trait.");
		
		config.save();
		
	}
	
	public static void registerConfig(FMLPreInitializationEvent event){
		RandomLoot.config = new File(event.getModConfigurationDirectory() + "/" + RandomLoot.MODID);
		RandomLoot.config.mkdirs();
		init(new File(RandomLoot.config.getPath(), RandomLoot.MODID + ".cfg"));
	}
	
}
