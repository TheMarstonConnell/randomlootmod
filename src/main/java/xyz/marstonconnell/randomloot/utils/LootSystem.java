package xyz.marstonconnell.randomloot.utils;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Set;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SwordItem;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.RLItems;

public class LootSystem {

	static ArrayList<Item> allSwords;
	static ArrayList<Item> allAxes;
	static ArrayList<Item> allPicks;
	static ArrayList<Item> allShovels;
	static ArrayList<Item> allBows;

	public static void init() {

		allSwords = new ArrayList<Item>();
		allAxes = new ArrayList<Item>();
		allPicks = new ArrayList<Item>();
		allShovels = new ArrayList<Item>();
		allBows = new ArrayList<Item>();

		Set<Entry<RegistryKey<Item>, Item>> allItemLocations = ForgeRegistries.ITEMS.getEntries();
		ArrayList<Item> allItems = new ArrayList<Item>();
		for (Entry<RegistryKey<Item>, Item> e : allItemLocations) {
			allItems.add(e.getValue());
		}
		allItemLocations = null;

		for (Item i : allItems) {
			Set<ToolType> types = i.getToolTypes(new ItemStack(i));
			
			if(types.contains(ToolType.AXE)) {
				allAxes.add(i);
			}
			
			if(types.contains(ToolType.PICKAXE)) {
				allPicks.add(i);
			}
			
			if(types.contains(ToolType.SHOVEL)) {
				allPicks.add(i);
			}
			
			if(i instanceof SwordItem) {
				allSwords.add(i);
			}
			
			if(i instanceof ShootableItem) {
				allBows.add(i);
			}
			
			
			
		}

		

	}

	private static int adjustChance(int amt, float adjustment, int ratio) {
		return (int) ((amt / ratio) * adjustment + (amt - amt / (ratio)));
	}
	
	public static Item getPersonalItem(ServerPlayerEntity playerIn) {

		ServerStatisticsManager ssm = playerIn.getStats();

		
		float swordsUsed = 1;
		float axesUsed = 1;
		float bowsUsed = 1;
		float picksUsed = 1;
		float shovelsUsed = 1;
		
		for(Item i : allSwords) {
			swordsUsed += ssm.getValue(Stats.ITEM_USED.get(i));
		}
		
		for(Item i : allAxes) {
			axesUsed += ssm.getValue(Stats.ITEM_USED.get(i));
		}

		
		for(Item i : allPicks) {
			picksUsed += ssm.getValue(Stats.ITEM_USED.get(i));
		}
		
		for(Item i : allBows) {
			bowsUsed += ssm.getValue(Stats.ITEM_USED.get(i));
		}
		
		for(Item i : allShovels) {
			shovelsUsed += ssm.getValue(Stats.ITEM_USED.get(i));
		}
		
		float dmgAbsorbed = ssm.getValue(Stats.CUSTOM.get(Stats.DAMAGE_ABSORBED));
		float dmgTaken = ssm.getValue(Stats.CUSTOM.get(Stats.DAMAGE_TAKEN)) + 0.00001f;
		
		float totalDamage = dmgTaken + dmgAbsorbed;
		
		float armorRatio = 1 - dmgTaken / totalDamage;
		
		float total = swordsUsed + bowsUsed + shovelsUsed + picksUsed + axesUsed;
		
		WeightedChooser<Item> wc = new WeightedChooser<Item>();
		
		int ratio = 2;
		float timePlaying = ssm.getValue(Stats.CUSTOM.get(Stats.PLAY_ONE_MINUTE));
		
		float timeFactor = 2000;
		
		ratio += Math.pow(10, (-timePlaying) / timeFactor + 1);
		
		
		
		wc.addChoice(RLItems.random_sword, adjustChance(Config.SWORD_CHANCE.get(), swordsUsed/total, ratio));
		wc.addChoice(RLItems.random_pick, adjustChance(Config.PICK_CHANCE.get(), picksUsed/ total, ratio));
		wc.addChoice(RLItems.random_axe, adjustChance(Config.AXE_CHANCE.get(), axesUsed/total, ratio));
		wc.addChoice(RLItems.random_spade, adjustChance(Config.SPADE_CHANCE.get(), shovelsUsed/total, ratio));
		wc.addChoice(RLItems.random_bow, adjustChance(Config.BOW_CHANCE.get(), bowsUsed/total, ratio));

		wc.addChoice(RLItems.HEAVY_BOOTS, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.HEAVY_CHEST, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.HEAVY_HELMET, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.HEAVY_LEGS, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.TITANIUM_BOOTS, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.TITANIUM_CHEST, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.TITANIUM_HELMET, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		wc.addChoice(RLItems.TITANIUM_LEGS, adjustChance(Config.ARMOR_CHANCE.get(), armorRatio, ratio));
		
		if(RandomLootMod.DEBUG) {
			String data = "[DEBUG] Swords: " + swordsUsed + " | Picks: " + picksUsed + " | Axes: " + axesUsed + " | Shovels: " + shovelsUsed + " | Bows: " + bowsUsed + " | Armor: " + armorRatio + ".";
			playerIn.sendMessage(new StringTextComponent(data), playerIn.getUniqueID());

		}
		
		return wc.getRandomObject();

	}

}
