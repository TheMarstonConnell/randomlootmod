package xyz.marstonconnell.randomloot.utils;

import java.util.HashMap;
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
	public static final String CATEGORY_TOOLS = "tools";
	public static final String CATEGORY_TRAITS = "traits";
	public static final String CATEGORY_CASES = "cases";
	
	
	public static IntValue BASE_PICKAXE_DAMAGE;
	public static DoubleValue BASE_PICKAXE_ATTACK_SPEED;

	public static ForgeConfigSpec COMMON_CONFIG;
	
	public static ForgeConfigSpec.BooleanValue DATA_COLLECT;

	public static ForgeConfigSpec.IntValue BASIC_CHANCE;
	public static ForgeConfigSpec.IntValue GOLD_CHANCE;
	public static ForgeConfigSpec.IntValue TITAN_CHANCE;
	public static ForgeConfigSpec.IntValue DROP_CHANCE;

	public static ForgeConfigSpec.IntValue TRAIT_RATIO;

	public static ForgeConfigSpec.IntValue BASE_SWORD_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_SWORD_SPEED;

	public static ForgeConfigSpec.IntValue BASE_THROWABLE_DAMAGE;

	public static ForgeConfigSpec.IntValue BASE_AXE_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_AXE_ATTACK_SPEED;

	public static ForgeConfigSpec.IntValue BASE_SPADE_DAMAGE;
	public static ForgeConfigSpec.DoubleValue BASE_SPADE_ATTACK_SPEED;

	public static ForgeConfigSpec.IntValue BASE_ARMOR;
	public static ForgeConfigSpec.DoubleValue BASE_TOUGHNESS;

	public static ForgeConfigSpec.IntValue BASIC_ROLLS;
	public static ForgeConfigSpec.IntValue GOLD_ROLLS;
	public static ForgeConfigSpec.IntValue TITAN_ROLLS;

	public static ForgeConfigSpec.IntValue SWORD_CHANCE;
	public static ForgeConfigSpec.IntValue PICK_CHANCE;
	public static ForgeConfigSpec.IntValue AXE_CHANCE;
	public static ForgeConfigSpec.IntValue SPADE_CHANCE;
	public static ForgeConfigSpec.IntValue BOW_CHANCE;
	public static ForgeConfigSpec.IntValue ARMOR_CHANCE;
	public static ForgeConfigSpec.IntValue THROWABLE_CHANCE;

	public static ForgeConfigSpec.IntValue STARTING_XP;

	public static IntValue MONSTERS_DROP;
	public static IntValue ANIMAL_DROP;
	public static IntValue BOSS_DROP;

	public static IntValue BASIC_COMMON;
	public static IntValue BASIC_RARE;
	public static IntValue BASIC_LEGEND;

	public static IntValue BETTER_COMMON;
	public static IntValue BETTER_RARE;
	public static IntValue BETTER_LEGEND;

	public static IntValue TITAN_COMMON;
	public static IntValue TITAN_RARE;
	public static IntValue TITAN_LEGEND;

	public static HashMap<String, ForgeConfigSpec.BooleanValue> traitsEnabled = new HashMap<String, ForgeConfigSpec.BooleanValue>();

	static {

		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

		COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

		BASIC_CHANCE = COMMON_BUILDER.comment("Weight of a basic case dropping").defineInRange("basic_chance", 70, 0,
				100);
		GOLD_CHANCE = COMMON_BUILDER.comment("Weight of a golden case dropping").defineInRange("gold_chance", 20, 0,
				100);
		TITAN_CHANCE = COMMON_BUILDER.comment("Weight of a titan case dropping").defineInRange("titan_chance", 10, 0,
				100);

		DROP_CHANCE = COMMON_BUILDER.comment("Chance of a case dropping #DEPRECATED").defineInRange("total_chance", 0,
				0, 100);

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

		ARMOR_CHANCE = COMMON_BUILDER.comment("Weight for any given piece of armor to drop from case")
				.defineInRange("armor_weight", 1, 0, 100);

		MONSTERS_DROP = COMMON_BUILDER.comment("Monsters drop chance").defineInRange("monster_drops", 50, 0, 1000);

		ANIMAL_DROP = COMMON_BUILDER.comment("Animals drop chance").defineInRange("animal_drops", 0, 0, 1000);

		BOSS_DROP = COMMON_BUILDER.comment("Bosses drop chance").defineInRange("boss_drops", 1000, 0, 1000);

		
		DATA_COLLECT = COMMON_BUILDER.comment("Allow anonymous data collection?").define("data", true);
		
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Case Distribution").push(CATEGORY_CASES);

		
		BASIC_COMMON = COMMON_BUILDER.comment("Basic Case common drop weight").defineInRange("bsc_weight", 90, 0, 100);
		BASIC_RARE = COMMON_BUILDER.comment("Basic Case rare drop weight").defineInRange("bsr_weight", 10, 0, 100);
		BASIC_LEGEND = COMMON_BUILDER.comment("Basic Case legendary drop weight").defineInRange("bsl_weight", 0, 0, 100);
		
		BETTER_COMMON = COMMON_BUILDER.comment("Gold Case common drop weight").defineInRange("brc_weight", 50, 0, 100);
		BETTER_RARE = COMMON_BUILDER.comment("Gold Case rare drop weight").defineInRange("brr_weight", 45, 0, 100);
		BETTER_LEGEND = COMMON_BUILDER.comment("Gold Case legendary drop weight").defineInRange("brl_weight", 5, 0, 100);

		TITAN_COMMON = COMMON_BUILDER.comment("Titan Case common drop weight").defineInRange("btc_weight", 20, 0, 100);
		TITAN_RARE = COMMON_BUILDER.comment("Titan Case rare drop weight").defineInRange("btr_weight", 60, 0, 100);
		TITAN_LEGEND = COMMON_BUILDER.comment("Titan Case legendary drop weight").defineInRange("btl_weight", 20, 0, 100);

		
		COMMON_BUILDER.pop();

		COMMON_BUILDER.comment("Tool Settings").push(CATEGORY_TOOLS);

		BASE_SWORD_DAMAGE = COMMON_BUILDER.comment("Minimum damage a sword can do.").defineInRange("sword_damage", 5, 0,
				100);

		BASE_SWORD_SPEED = COMMON_BUILDER.comment("Minimum speed a sword can have.").defineInRange("sword_speed", -2.9,
				-4.0, 4.0);

		BASE_THROWABLE_DAMAGE = COMMON_BUILDER.comment("Minimum damage a throwable can do.")
				.defineInRange("throw_damage", 5, 0, 100);

		BASE_PICKAXE_DAMAGE = COMMON_BUILDER.comment("Minimum damage a pickaxe can do.").defineInRange("pick_damage", 3,
				0, 100);

		BASE_PICKAXE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed a pickaxe can have.")
				.defineInRange("pick_a_speed", -2.8, -4.0, 4.0);

		BASE_AXE_DAMAGE = COMMON_BUILDER.comment("Minimum damage an axe can do.").defineInRange("axe_damage", 7, 0,
				100);

		BASE_AXE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed an axe can have.")
				.defineInRange("axe_a_speed", -3.5, -4.0, 4.0);

		BASE_ARMOR = COMMON_BUILDER.comment("Minimum armor points armor can have.").defineInRange("armor", 4, 0, 100);

		BASE_TOUGHNESS = COMMON_BUILDER.comment("Minimum armor points armor can have.").defineInRange("toughness", 0.1,
				0, 100);

		BASE_SPADE_DAMAGE = COMMON_BUILDER.comment("Minimum damage a shovel can do.").defineInRange("spade_damage", 3,
				0, 100);

		BASE_SPADE_ATTACK_SPEED = COMMON_BUILDER.comment("Minimum attack speed a shovel can have.")
				.defineInRange("spade_a_speed", -2.8, -4.0, 4.0);

		BASIC_ROLLS = COMMON_BUILDER.comment("Initital Rolls for traits/stats").defineInRange("basic_rolls", 2, 0, 100);

		GOLD_ROLLS = COMMON_BUILDER.defineInRange("gold_rolls", 4, 0, 100);

		TITAN_ROLLS = COMMON_BUILDER.defineInRange("titan_rolls", 10, 0, 100);

		STARTING_XP = COMMON_BUILDER.comment("Initital Max XP for tools.").defineInRange("init_xp", 256, 0, 10000);

		TRAIT_RATIO = COMMON_BUILDER.comment(
				"Odds of an item getting a buff rather than a trait on a stats roll (editor upgrade, tool init, etc.)")
				.defineInRange("trait_ratio", 5, 0, 100);

		THROWABLE_CHANCE = COMMON_BUILDER.comment("Weight for a throwable weapon to drop from case")
				.defineInRange("throwing_weight", 6, 0, 100);

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