package xyz.marstonconnell.randomloot.init;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.IRLTool;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;

public class ItemFactory {

	public static final int CURRENT_TOOL_VERSION = 1;
	
	static Random rand = new Random();

	public static void applyToken(ItemStack stack) {
		if (rand.nextInt(Config.TRAIT_RATIO.get()) <= 1) {
			giftNewTrait(stack);
		} else {
			buffItemStats(stack);
		}
	}

	private static void buffItemStats(ItemStack stack) {

		if (stack.getItem() instanceof IRLTool) {
			((IRLTool) stack.getItem()).upgradeTool(stack);
		}

	}

	private static void giftNewTrait(ItemStack stack) {

		List<BasicTag> allTags = TagHelper.getCompatibleTags(stack);		

		BasicTag t = allTags.get(rand.nextInt(allTags.size()));

		TagHelper.addTag(stack, t);

	}

	public static ItemStack forgeItem(ItemStack stack, int rarity) {

		WeightedChooser<Integer> wc = new WeightedChooser<Integer>();
		switch (rarity) {
		case 0:
			wc.addChoice(0, Config.BASIC_COMMON.get());
			wc.addChoice(1, Config.BASIC_RARE.get());
			wc.addChoice(2, Config.BASIC_LEGEND.get());
			break;

		case 1:
			wc.addChoice(0, Config.BETTER_COMMON.get());
			wc.addChoice(1, Config.BETTER_RARE.get());
			wc.addChoice(2, Config.BETTER_LEGEND.get());
			break;

		case 2:
			wc.addChoice(0, Config.TITAN_COMMON.get());
			wc.addChoice(1, Config.TITAN_RARE.get());
			wc.addChoice(2, Config.TITAN_LEGEND.get());
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

		((IRLTool) stack.getItem()).setStats(stack);

		System.out.println("Rolling for item " + totalRolls + " times...");
		for (int i = 0; i < totalRolls; i++) {
			applyToken(stack);
		}

		((IRLTool) stack.getItem()).updateStats(stack);

		BaseTool.setIntNBT(stack, "rl_tool_version", CURRENT_TOOL_VERSION);
		
		// naming item
		BaseTool.setName(stack, ItemUtils.nameItem(((IRLTool) stack.getItem()).getItemType()));

		BaseTool.setTexture(stack, rand.nextInt(((IRLTool) stack.getItem()).getVariants() - 1) + 1);
		BaseTool.setMaxXP(stack, Config.STARTING_XP.get());

		BaseTool.setLore(stack);

		// stack.setDisplayName(new StringTextComponent(color.toString() +
		// BaseTool.getName(stack)));

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		CompoundNBT display = nbt.getCompound("display");
		display.putString("Name",
				"{\"text\":\"" + BaseTool.getName(stack) + "\", \"color\":\"" + color.name().toLowerCase() + "\",\"italic\":false}");

		stack.setTag(nbt);

		return stack;
	}
}
