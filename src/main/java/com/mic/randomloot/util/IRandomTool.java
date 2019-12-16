package com.mic.randomloot.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public interface IRandomTool {
	public void setLore(ItemStack stack, EntityLivingBase player);
	public ItemStack chooseTexture(ItemStack stack, int integer);
	public void setName(ItemStack stack);
}
