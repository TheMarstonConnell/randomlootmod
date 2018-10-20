package com.mic.randomloot.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ClickItem extends ItemBase{

	private Item replace;
	
	public ClickItem(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(replace));
		playerIn.onKillCommand();
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
