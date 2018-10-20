package com.mic.randomloot.util;

import com.mic.randomloot.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs{

	public ModTab() {
		super("randomlootTab");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.BASIC_CASE);
	}
	
	

}
