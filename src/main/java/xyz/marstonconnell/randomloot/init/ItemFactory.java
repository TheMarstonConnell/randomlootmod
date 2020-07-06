package xyz.marstonconnell.randomloot.init;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.IRLTool;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;

public class ItemFactory {

	static Random rand = new Random();
	
	public static void applyToken(ItemStack stack) {
		if(rand.nextInt(4) < 1) {
			giftNewTrait(stack);
		}else {
			buffItemStats(stack);
		}
	}
	

	private static void buffItemStats(ItemStack stack) {

		if(stack.getItem() instanceof IRLTool) {
			((IRLTool) stack.getItem()).upgradeTool(stack);
		}
		
		
	}


	private static void giftNewTrait(ItemStack stack) {
		List<BasicTag> tags = ((IRLTool)stack.getItem()).getAllowedTags();
		
		List<BasicTag> allTags = TagHelper.getTagList(stack);
		
		for(int i = 0; i < allTags.size(); i ++) {
			if(tags.contains(allTags.get(i))) {
				tags.remove(allTags.get(i));
			}
		}
		
		BasicTag t = tags.get(rand.nextInt(tags.size()));
		
		TagHelper.addTag(stack, t.name);
		
		
	}


	public static ItemStack forgeItem(ItemStack stack, int rarity) {

		WeightedChooser<Integer> wc = new WeightedChooser<Integer>();
		switch (rarity) {
		case 0:
			wc.addChoice(0, 60);
			wc.addChoice(1, 30);
			wc.addChoice(2, 10);
			break;

		case 1:
			wc.addChoice(0, 40);
			wc.addChoice(1, 40);
			wc.addChoice(2, 20);
			break;

		case 2:
			wc.addChoice(0, 20);
			wc.addChoice(1, 50);
			wc.addChoice(2, 30);
			break;

		default:
			wc.addChoice(0, 1);
			break;
		}
		
		int toolRaririty = wc.getRandomObject();
		
		int totalRolls = 0;
		
		
		TextFormatting color = TextFormatting.GRAY;

		switch (toolRaririty) {
		case 0:
			totalRolls = Config.BASIC_ROLLS.get();
			color = TextFormatting.WHITE;
			break;
		case 1:
			totalRolls = Config.GOLD_ROLLS.get();
			color = TextFormatting.GOLD;
			break;
		case 2:
			totalRolls = Config.TITAN_ROLLS.get();
			color = TextFormatting.LIGHT_PURPLE;
			break;
		}
		
		
		((IRLTool)stack.getItem()).setStats(stack);
		
		System.out.println("Rolling for item " + totalRolls + " times...");
		for(int i = 0; i < totalRolls; i ++) {
			applyToken(stack);
		}

		((IRLTool)stack.getItem()).updateStats(stack);

		
		// naming item
		BaseTool.setName(stack, ItemUtils.nameItem(((IRLTool) stack.getItem()).getItemType()));

		BaseTool.setTexture(stack, rand.nextInt(((IRLTool) stack.getItem()).getVariants()));
		BaseTool.setMaxXP(stack, Config.STARTING_XP.get());

		
		
		BaseTool.setLore(stack);
		
		
		
		stack.setDisplayName(new StringTextComponent(color + BaseTool.getName(stack)));

		
		
		return stack;
	}
}
