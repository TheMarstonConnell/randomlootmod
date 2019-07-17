package com.mic.randomloot.blocks.containers;

import com.mic.randomloot.blocks.tileentities.RepairTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RepairContainer extends Container{
	private final int numRows;
	private final RepairTileEntity chestInventory;
	
	public RepairContainer(InventoryPlayer playerInv, RepairTileEntity tileEntity, EntityPlayer player) 
	{
		this.chestInventory = tileEntity;
		this.numRows = tileEntity.getSizeInventory() / 9;
		tileEntity.openInventory(player);
		

				this.addSlotToContainer(new Slot(tileEntity, 0, 0, 0));

		
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.chestInventory.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) 
	{
		super.onContainerClosed(playerIn);
		chestInventory.closeInventory(playerIn);
	}
	
//	@Override
//	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
//	{
//		ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.inventorySlots.get(index);
//
//        if (slot != null && slot.getHasStack())
//        {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//
//            if (index < this.numRows * 9)
//            {
//                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
//                {
//                    return ItemStack.EMPTY;
//                }
//            }
//            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
//            {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty())
//            {
//                slot.putStack(ItemStack.EMPTY);
//            }
//            else
//            {
//                slot.onSlotChanged();
//            }
//        }
//
//        return itemstack;
//	}
	
	public RepairTileEntity getChestInventory()
	{
		return this.chestInventory;
	}
}
