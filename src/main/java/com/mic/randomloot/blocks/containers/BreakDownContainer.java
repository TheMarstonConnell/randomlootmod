package com.mic.randomloot.blocks.containers;

import com.mic.randomloot.blocks.recipes.BreakerRecipes;
import com.mic.randomloot.blocks.tileentities.TileEntityBreaker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BreakDownContainer extends Container{
	private final IInventory tileBreaker;
    private int breakTime;
    private int totalbreakTime;
    private int breakerBreakTime;
    private int currentItemBreakTime;

    public BreakDownContainer(InventoryPlayer playerInventory, IInventory tileBreaker)
    {
        this.tileBreaker = tileBreaker;
        this.addSlotToContainer(new Slot(tileBreaker, 0, 56, 17));
        this.addSlotToContainer(new SlotBreakerFuel(tileBreaker, 1, 56, 53));
        this.addSlotToContainer(new SlotBreakerOutput(playerInventory.player, tileBreaker, 2, 116, 35));

        // ranging from 9 to 36
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //ranging from 0 to 8
        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileBreaker);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.breakTime != this.tileBreaker.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileBreaker.getField(2));
            }

            if (this.breakerBreakTime != this.tileBreaker.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileBreaker.getField(0));
            }

            if (this.currentItemBreakTime != this.tileBreaker.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileBreaker.getField(1));
            }

            if (this.totalbreakTime != this.tileBreaker.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tileBreaker.getField(3));
            }
        }

        this.breakTime = this.tileBreaker.getField(2);
        this.breakerBreakTime = this.tileBreaker.getField(0);
        this.currentItemBreakTime = this.tileBreaker.getField(1);
        this.totalbreakTime = this.tileBreaker.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileBreaker.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileBreaker.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
    	ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 1 && index != 0) 
			{		
				Slot slot1 = (Slot)this.inventorySlots.get(index + 1);
				
				if(!BreakerRecipes.getInstance().getSinteringResult(stack1).isEmpty())
				{
					if(!this.mergeItemStack(stack1, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
					else if(TileEntityBreaker.isItemFuel(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(TileEntityBreaker.isItemFuel(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(TileEntityBreaker.isItemFuel(stack1))
					{
						if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(index >= 4 && index < 31)
					{
						if(!this.mergeItemStack(stack1, 31, 40, false)) return ItemStack.EMPTY;
					}
					else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false))
					{
						return ItemStack.EMPTY;
					}
				}
			} 
			else if(!this.mergeItemStack(stack1, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
