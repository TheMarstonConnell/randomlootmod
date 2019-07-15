package com.mic.randomloot.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IReforgeable {
	public ItemStack reforge(ItemStack stack);
	public void setLore(ItemStack stack, EntityLivingBase player);
}
