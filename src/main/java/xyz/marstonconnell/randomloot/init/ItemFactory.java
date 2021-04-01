package xyz.marstonconnell.randomloot.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.BasicTag;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tools.ToolUtilities;
import xyz.marstonconnell.randomloot.tools.IRLTool;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.utils.DataCollection;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;

public class ItemFactory {

	public static final int CURRENT_TOOL_VERSION = 1;
	public static final String RL_FOUND_IN = "rl_found_in";
	public final static String[] FOUND_IN_TEXTS = {"Found in the heart of the", "Discovered deep in the", "Found in the", "Rightfully claimed at the", "Taken from the", "Gifted by the gods in the", "Uncovered at the", "Found deep in the"};

	
	static Random rand = new Random();

	public static void applyToken(ItemStack stack, World worldIn) {
		if (rand.nextInt(Config.TRAIT_RATIO.get()) <= 1) {
			giftNewTrait(stack, worldIn);
		} else {
			buffItemStats(stack, worldIn);
		}
	}

	private static void buffItemStats(ItemStack stack, World worldIn) {

		if (stack.getItem() instanceof IRLTool) {
			((IRLTool) stack.getItem()).upgradeTool(stack, worldIn);
		}

	}

	private static void giftNewTrait(ItemStack stack, World worldIn) {

		List<BasicTag> choosingFrom = new ArrayList<BasicTag>();
		
		
		List<BasicTag> allTags = TagHelper.getCompatibleTags(stack);	
		List<BasicTag> currentTags = TagHelper.getTagList(stack);
		
		for(BasicTag tag : currentTags) {
			if(!tag.natural || !tag.enabled) {
				continue;
			}
			
			BasicTag newTag = TagHelper.copyTag(tag).setLevel(0);
			choosingFrom.add(newTag);

		}
		
		for(BasicTag tag : allTags) {
			if(!tag.natural || !tag.enabled) {
				continue;
			}
			
			choosingFrom.add(tag);
		}
		

		BasicTag t = choosingFrom.get(rand.nextInt(choosingFrom.size()));

		TagHelper.addTag(stack, t, worldIn);

	}

	public static ItemStack forgeItem(ItemStack stack, int rarity, World worldIn, BlockPos pos) {

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
			applyToken(stack, worldIn);
		}

		((IRLTool) stack.getItem()).updateStats(stack);

		ToolUtilities.setIntNBT(stack, "rl_tool_version", CURRENT_TOOL_VERSION);
		
		ToolUtilities.setStringNBT(stack, RL_FOUND_IN, FOUND_IN_TEXTS[(int) (Math.random() * FOUND_IN_TEXTS.length)] +  " " + TagHelper.convertToTitleCaseIteratingChars(worldIn.getBiome(pos).getRegistryName().getPath()));
		
		// naming item
		ToolUtilities.setName(stack, ItemUtils.nameItem(((IRLTool) stack.getItem()).getItemType()));

		ToolUtilities.setTexture(stack, rand.nextInt(((IRLTool) stack.getItem()).getVariants() - 1) + 1);
		ToolUtilities.setMaxXP(stack, Config.STARTING_XP.get());

		ToolUtilities.setLore(stack, worldIn);

		// stack.setDisplayName(new StringTextComponent(color.toString() +
		// ToolUtilities.getName(stack)));

		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		CompoundNBT display = nbt.getCompound("display");
		display.putString("Name",
				"{\"text\":\"" + ToolUtilities.getName(stack) + "\", \"color\":\"" + color.name().toLowerCase() + "\",\"italic\":false}");

		stack.setTag(nbt);

		DataCollection.uploadTool(stack);
		
		return stack;
	}
}
