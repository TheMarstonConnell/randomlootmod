package com.mic.randomloot.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IRandomTool {
	public void setLore(ItemStack stack, EntityLivingBase player);
	public ItemStack chooseTexture(ItemStack stack, int integer);

}
