package xyz.marstonconnell.randomloot.tools;

import java.util.List;

import net.minecraft.item.ItemStack;
import xyz.marstonconnell.randomloot.tags.BasicTag;

public interface IRLTool {
	void setStats(ItemStack stack);
	void updateStats(ItemStack stack);
	void upgradeTool(ItemStack stack);
	List<String> getStatsLore(ItemStack stack);
	String getItemType();
	int getVariants();
	
	List<BasicTag> getAllowedTags();
}
