package com.mic.randomloot.util.handlers;

import com.mic.randomloot.blocks.containers.BreakDownContainer;
import com.mic.randomloot.blocks.gui.GuiBreaker;
import com.mic.randomloot.blocks.tileentities.TileEntityBreaker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler

{
	public static final int GUI_BREAKER = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUI_BREAKER)
			return new BreakDownContainer(player.inventory,
					(TileEntityBreaker) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUI_BREAKER)
			return new GuiBreaker(player.inventory,
					(TileEntityBreaker) world.getTileEntity(new BlockPos(x, y, z)));
		
		return null;
	}
}