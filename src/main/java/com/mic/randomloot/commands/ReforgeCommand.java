package com.mic.randomloot.commands;

import java.util.ArrayList;
import java.util.List;

import com.mic.randomloot.items.CaseItem;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.NetworkHandler;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ReforgeCommand implements ICommand {
	private final List aliases;

	public ReforgeCommand() {
		aliases = new ArrayList();
		aliases.add("reforge");
	}

	public String getCommandName() {
		return "reforge";
	}

	public String getCommandUsage(ICommandSender var1) {
		return "reforge";
	}

	public List getAliases() {
		return this.aliases;
	}

	@Override
	public boolean isUsernameIndex(String[] var1, int var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "reforge";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "reforge";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (sender instanceof EntityPlayer) {
			System.out.println("Runnign reforge Command");
			EntityPlayer player = (EntityPlayer) sender;
			if (player.getHeldItemMainhand().getItem() instanceof IReforgeable) {

					NetworkHandler.reforge();

				
				
				sender.sendMessage(new TextComponentString("Item reforged."));
			} else {
				sender.sendMessage(new TextComponentString("This item cannot be reforged."));
			}
		}else {
			sender.sendMessage(new TextComponentString("Must be run by a player."));
		}

	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}
}