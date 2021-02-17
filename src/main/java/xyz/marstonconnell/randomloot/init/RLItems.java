package xyz.marstonconnell.randomloot.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.items.CaseItem;
import xyz.marstonconnell.randomloot.items.CraftingItem;
import xyz.marstonconnell.randomloot.items.RandomArmorMaterial;
import xyz.marstonconnell.randomloot.tools.BaseArmorMaterial;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.RLAxeItem;
import xyz.marstonconnell.randomloot.tools.RLBowItem;
import xyz.marstonconnell.randomloot.tools.RLPickaxeItem;
import xyz.marstonconnell.randomloot.tools.RLShovelItem;
import xyz.marstonconnell.randomloot.tools.RLSwordItem;
import xyz.marstonconnell.randomloot.tools.RLThrowableItem;
import xyz.marstonconnell.randomloot.tools.RandomArmor;

public class RLItems {
	
	
	public static List<Item> ITEMS = new ArrayList<Item>();

	public static final Item BASIC_ITEM_CASE = new CaseItem("basic_case", 0);
	public static final Item BETTER_ITEM_CASE = new CaseItem("golden_case", 1);
	public static final Item BEST_ITEM_CASE = new CaseItem("titan_case", 2);

	public static final RLSwordItem random_sword = new RLSwordItem("sword", 10, -2.4f);
	public static final RLPickaxeItem random_pick = new RLPickaxeItem("pickaxe");

	public static final RLAxeItem random_axe = new RLAxeItem("axe");
	public static final RLShovelItem random_spade = new RLShovelItem("shovel");
	public static final RLBowItem random_bow = new RLBowItem("rl_bow");

//	public static final RLThrowableItem THROWABLE_ITEM = new RLThrowableItem("rl_throwable");

	
	public static final Item basic_shard = new CraftingItem("random_shard", true);
	public static final Item better_shard = new CraftingItem("random_chunk", true);
	public static final Item best_shard = new CraftingItem("random_cluster", true);
	public static final Item TRAIT_HOLDER = new CraftingItem("trait_holder", true);
	
	public static RandomArmorMaterial hm = new RandomArmorMaterial("heavy", 33,  new int[] { 4, 7, 8, 4 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> {
	      return Ingredient.fromItems(RLItems.best_shard);});


	

	public static final Item HEAVY_BOOTS = new RandomArmor("heavy_boots", hm, EquipmentSlotType.FEET);
	public static final Item HEAVY_LEGS = new RandomArmor("heavy_legs", hm, EquipmentSlotType.LEGS);
	public static final Item HEAVY_CHEST = new RandomArmor("heavy_chest", hm, EquipmentSlotType.CHEST);
	public static final Item HEAVY_HELMET = new RandomArmor("heavy_helmet", hm, EquipmentSlotType.HEAD);

	public static RandomArmorMaterial tm = new RandomArmorMaterial("titanium", 33,  new int[] { 4, 7, 8, 4 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> {
	      return Ingredient.fromItems(RLItems.best_shard);});

	

	public static final Item TITANIUM_BOOTS = new RandomArmor("titanium_boots", tm, EquipmentSlotType.FEET);
	public static final Item TITANIUM_LEGS = new RandomArmor("titanium_legs", tm, EquipmentSlotType.LEGS);
	public static final Item TITANIUM_CHEST = new RandomArmor("titanium_chest", tm, EquipmentSlotType.CHEST);
	public static final Item TITANIUM_HELMET = new RandomArmor("titanium_helmet", tm, EquipmentSlotType.HEAD);

}
