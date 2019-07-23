package com.mic.randomloot.tags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class BasicTag {
	public String name;
	public TextFormatting color;
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		TagHelper.allTags.add(this);
	}
}
