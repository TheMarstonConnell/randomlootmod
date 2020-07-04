package xyz.marstonconnell.randomloot.tools;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRLTool {
	void setStats(ItemStack stack);
	void updateStats(ItemStack stack);
	void upgradeTool(ItemStack stack);
	List<String> getStatsLore(ItemStack stack);
}
