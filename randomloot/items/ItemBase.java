package com.mic.randomloot.items;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item{

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(RandomLoot.randomlootTab);

		ModItems.ITEMS.add(this);

	}

//	@Override
//	public void registerModels() {
//		RandomLoot.proxy.registerItemRenderer(this, 0, "inventory");
//	}

}
