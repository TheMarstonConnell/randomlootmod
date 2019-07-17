package com.mic.randomloot.blocks.tileentities;

import java.util.List;
import java.util.Random;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.blocks.containers.RepairContainer;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class RepairTileEntity extends TileEntityLockableLoot implements ITickable {
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	public boolean activated = false;
	int coolDown = 0;

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this.chestContents) {
			if (!stack.isEmpty())
				return false;
		}

		return true;
	}

	public void addItem(EntityPlayer playerIn, ItemStack item) {

		if (chestContents.get(0).getItem().equals(Items.AIR)) {
			System.out.println("Chest is empty");
			if (!item.getItem().equals(Items.AIR)) {
				chestContents.set(0, item);
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.AIR));
				System.out.println("Giving item to repair station");
				this.activated = true;
			}
		} else {
			System.out.println("Chest isn't empty");

			if (item.getItem().equals(Items.AIR)) {
				ItemStack ret = chestContents.get(0);
				chestContents.set(0, new ItemStack(Items.AIR));
				System.out.println("Getting item from repair station");
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ret);
				this.activated = false;
			} else {
				playerIn.sendMessage(new TextComponentString("Station is full."));

			}
		}

	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.repair_station";
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

		if (!this.checkLootAndRead(compound))
			ItemStackHelper.loadAllItems(compound, chestContents);
		if (compound.hasKey("CustomName", 8))
			this.customName = compound.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!this.checkLootAndWrite(compound))
			ItemStackHelper.saveAllItems(compound, chestContents);
		if (compound.hasKey("CustomName", 8))
			compound.setString("CustomName", this.customName);

		return compound;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new RepairContainer(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return RandomLoot.MODID + ":repair_station";
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	@Override
	public void update() {
		if (this.world.isBlockPowered(this.getPos())) {

			this.coolDown++;
			if (coolDown > ConfigHandler.repairStationCooldown) {
				coolDown = 0;
				if (chestContents.get(0).getItem().isDamageable()) {
					ItemStack stack = chestContents.get(0);

					int damage = stack.getItemDamage();
					int max = stack.getMaxDamage();
					// System.out.println("Tools damage is " + (max - damage) + "/" + max);

					if ((max - damage) < max) {

						stack.setItemDamage(damage - 1);
						chestContents.set(0, stack);

					}else {
						this.activated = false;
					}

				}
			}
		}

	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}
}
