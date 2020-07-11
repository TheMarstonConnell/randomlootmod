package xyz.marstonconnell.randomloot.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import xyz.marstonconnell.randomloot.items.CaseItem;
import xyz.marstonconnell.randomloot.items.CraftingItem;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.RLAxeItem;
import xyz.marstonconnell.randomloot.tools.RLBowItem;
import xyz.marstonconnell.randomloot.tools.RLPickaxeItem;
import xyz.marstonconnell.randomloot.tools.RLShovelItem;
import xyz.marstonconnell.randomloot.tools.RLSwordItem;

public class RLItems {

	  
	  public static final Item BASIC_ITEM_CASE = new CaseItem("basic_case", 0);
	  public static final Item BETTER_ITEM_CASE = new CaseItem("golden_case", 1);
	  public static final Item BEST_ITEM_CASE = new CaseItem("titan_case", 2);

	  public static final BaseTool random_sword = new RLSwordItem("sword", 10, -2.4f);
	  public static final BaseTool random_pick = new RLPickaxeItem("pickaxe");
	  
	  public static final BaseTool random_axe = new RLAxeItem("axe");
	  public static final BaseTool random_spade = new RLShovelItem("shovel");
	  public static final RLBowItem random_bow = new RLBowItem("rl_bow");

	  
	  public static final Item basic_shard = new CraftingItem("random_shard", true);
	  public static final Item better_shard = new CraftingItem("random_chunk", true);
	  public static final Item best_shard = new CraftingItem("random_cluster", true);
	  
	  public static final Item  TRAIT_HOLDER = new CraftingItem("trait_holder", true);
	  
	  
	  
}
