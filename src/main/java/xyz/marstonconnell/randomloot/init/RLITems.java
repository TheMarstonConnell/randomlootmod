package xyz.marstonconnell.randomloot.init;

import net.minecraft.item.Item;
import xyz.marstonconnell.randomloot.items.CaseItem;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.RLAxeItem;
import xyz.marstonconnell.randomloot.tools.RLPickaxeItem;
import xyz.marstonconnell.randomloot.tools.RLShovelItem;
import xyz.marstonconnell.randomloot.tools.RLSwordItem;

public class RLITems {

	  
	  public static final Item BASIC_ITEM_CASE = new CaseItem("basic_case", 0);
	  public static final Item BETTER_ITEM_CASE = new CaseItem("golden_case", 1);
	  public static final Item BEST_ITEM_CASE = new CaseItem("titan_case", 2);

	  public static final BaseTool random_sword = new RLSwordItem("sword", 10, -2.4f);
	  public static final BaseTool random_pick = new RLPickaxeItem("pickaxe");
	  public static final BaseTool random_shovel = new RLShovelItem("shovel");
	  public static final BaseTool random_axe = new RLAxeItem("axe");

}
