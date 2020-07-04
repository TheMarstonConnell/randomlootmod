package xyz.marstonconnell.randomloot.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

	public static final String CATEGORY_GENERAL = "general";

	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.IntValue BASIC_CHANCE;
	public static ForgeConfigSpec.IntValue GOLD_CHANCE;
	public static ForgeConfigSpec.IntValue TITAN_CHANCE;
	public static ForgeConfigSpec.IntValue DROP_CHANCE;

	public static ForgeConfigSpec.IntValue BASE_SWORD_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_SWORD_SPEED;
	
	public static ForgeConfigSpec.IntValue BASIC_ROLLS;
	public static ForgeConfigSpec.IntValue GOLD_ROLLS;
	public static ForgeConfigSpec.IntValue TITAN_ROLLS;

	
	public static ForgeConfigSpec.IntValue STARTING_XP;

	static {

		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

		COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

		BASIC_CHANCE = COMMON_BUILDER.comment("Weight of a basic case dropping").defineInRange("basic_chance", 70, 0,
				100);
		GOLD_CHANCE = COMMON_BUILDER.comment("Weight of a golden case dropping").defineInRange("gold_chance", 20, 0,
				100);
		TITAN_CHANCE = COMMON_BUILDER.comment("Weight of a titan case dropping").defineInRange("titan_chance", 70, 0,
				100);
		
		
		DROP_CHANCE = COMMON_BUILDER.comment("Chance of a case dropping").defineInRange("total_chance", 10, 0,
				100);
		
		
		BASE_SWORD_DAMAGE = COMMON_BUILDER.comment("Minimum damage a sword can do.").defineInRange("sword_damage", 7, 0,
				100);
		
		BASE_SWORD_SPEED = COMMON_BUILDER.comment("Minimum speed a sword can have.").defineInRange("sword_speed", -2.4, -4.0,
				4.0);
		
		
		BASIC_ROLLS = COMMON_BUILDER.comment("Initital Rolls for traits/stats").defineInRange("basic_rolls", 2, 0,
				100);
		
		GOLD_ROLLS = COMMON_BUILDER.defineInRange("gold_rolls", 5, 0,
				100);

		TITAN_ROLLS = COMMON_BUILDER.defineInRange("titan_rolls", 9, 0,
				100);
		
		STARTING_XP = COMMON_BUILDER.comment("Initital Max XP for tools.").defineInRange("init_xp", 256, 0,
				10000);
		
		COMMON_BUILDER.pop();


		COMMON_CONFIG = COMMON_BUILDER.build();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {
	}

}