package xyz.marstonconnell.randomloot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import xyz.marstonconnell.randomloot.tags.TagHelper;

@Mod.EventBusSubscriber
public class Config {

	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_TRAITS = "traits";

	public static IntValue BASE_PICKAXE_DAMAGE;
	public static DoubleValue BASE_PICKAXE_ATTACK_SPEED;

	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.IntValue BASIC_CHANCE;
	public static ForgeConfigSpec.IntValue GOLD_CHANCE;
	public static ForgeConfigSpec.IntValue TITAN_CHANCE;
	public static ForgeConfigSpec.IntValue DROP_CHANCE;

	public static ForgeConfigSpec.IntValue BASE_SWORD_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_SWORD_SPEED;

	public static ForgeConfigSpec.IntValue BASE_AXE_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_AXE_ATTACK_SPEED;

	public static ForgeConfigSpec.IntValue BASE_SPADE_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_SPADE_ATTACK_SPEED;

	public static ForgeConfigSpec.IntValue BASIC_ROLLS;
	public static ForgeConfigSpec.IntValue GOLD_ROLLS;
	public static ForgeConfigSpec.IntValue TITAN_ROLLS;

	public static ForgeConfigSpec.IntValue SWORD_CHANCE;
	public static ForgeConfigSpec.IntValue PICK_CHANCE;
	public static ForgeConfigSpec.IntValue AXE_CHANCE;
	public static ForgeConfigSpec.IntValue SPADE_CHANCE;
	public static ForgeConfigSpec.IntValue BOW_CHANCE;

	public static ForgeConfigSpec.IntValue STARTING_XP;

	public static HashMap<String, ForgeConfigSpec.BooleanValue> traitsEnabled = new HashMap<String, ForgeConfigSpec.BooleanValue>();

	static {

		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

		COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

		BASIC_CHANCE = COMMON_BUILDER.comment("Weight of a basic case dropping").defineInRange("basic_chance", 70, 0,
				100);
		GOLD_CHANCE = COMMON_BUILDER.comment("Weight of a golden case dropping").defineInRange("gold_chance", 20, 0,
				100);
		TITAN_CHANCE = COMMON_BUILDER.comment("Weight of a titan case dropping").defineInRange("titan_chance", 70, 0,
				100);

		DROP_CHANCE = COMMON_BUILDER.comment("Chance of a case dropping").defineInRange("total_chance", 10, 0, 100);

		BASE_SWORD_DAMAGE = COMMON_BUILDER.comment("Minimum damage a sword can do.").defineInRange("sword_damage", 7, 0,
				100);

		BASE_SWORD_SPEED = COMMON_BUILDER.comment("Minimum speed a sword can have.").defineInRange("sword_speed", -2.4,
				-4.0, 4.0);

		BASE_PICKAXE_DAMAGE = COMMON_BUILDER.comment("Minimum damage a pickaxe can do.").defineInRange("pick_damage", 3,
				0, 100);

		BASE_PICKAXE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed a pickaxe can have.")
				.defineInRange("pick_a_speed", -2.8, -4.0, 4.0);

		BASE_AXE_DAMAGE = COMMON_BUILDER.comment("Minimum damage an axe can do.").defineInRange("axe_damage", 9, 0,
				100);

		BASE_AXE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed an axe can have.")
				.defineInRange("axe_a_speed", -3, -4.0, 4.0);

		BASE_SPADE_DAMAGE = COMMON_BUILDER.comment("Minimum damage a shovel can do.").defineInRange("spade_damage", 3,
				0, 100);

		BASE_SPADE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed a shovel can have.")
				.defineInRange("spade_a_speed", -2.8, -4.0, 4.0);

		BASIC_ROLLS = COMMON_BUILDER.comment("Initital Rolls for traits/stats").defineInRange("basic_rolls", 2, 0, 100);

		GOLD_ROLLS = COMMON_BUILDER.defineInRange("gold_rolls", 5, 0, 100);

		TITAN_ROLLS = COMMON_BUILDER.defineInRange("titan_rolls", 9, 0, 100);

		STARTING_XP = COMMON_BUILDER.comment("Initital Max XP for tools.").defineInRange("init_xp", 256, 0, 10000);

		SWORD_CHANCE = COMMON_BUILDER.comment("Weight for a sword to drop from case").defineInRange("sword_weight", 20,
				0, 100);

		PICK_CHANCE = COMMON_BUILDER.comment("Weight for a pick to drop from case").defineInRange("pick_weight", 15, 0,
				100);

		SPADE_CHANCE = COMMON_BUILDER.comment("Weight for a shovel to drop from case").defineInRange("spade_weight", 8,
				0, 100);
		AXE_CHANCE = COMMON_BUILDER.comment("Weight for and axe to drop from case").defineInRange("axe_weight", 10, 0,
				100);

		BOW_CHANCE = COMMON_BUILDER.comment("Weight for a bow to drop from case").defineInRange("bow_weight", 8, 0,
				100);

		COMMON_BUILDER.pop();

		COMMON_BUILDER.comment("Traits Enabled").push(CATEGORY_TRAITS);

		for (int i = 0; i < TagHelper.allTags.size(); i++) {
			String name = TagHelper.allTags.get(i).name;
			traitsEnabled.put(name,
					COMMON_BUILDER.comment(TagHelper.convertToTitleCaseIteratingChars(name) + " Enabled?")
							.define(name + "_enabled", true));
		}

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