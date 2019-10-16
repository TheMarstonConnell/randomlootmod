package com.mic.randomloot.commands;

import java.util.ArrayList;
import java.util.List;

import com.mic.randomloot.util.handlers.NetworkHandler;
import com.mic.randomloot.util.handlers.StringNetworkHandler;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class AddTraitCommand implements ICommand {
	private final List<String> aliases;

	public AddTraitCommand() {
		aliases = new ArrayList<String>();
		aliases.add("trait");

	}

	public String getCommandName() {
		return "trait";
	}

	public String getCommandUsage(ICommandSender var1) {
		return "trait <add/remove> <trait>";
	}

	public List<String> getAliases() {
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
		return "trait";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "trait";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (sender instanceof EntityPlayer) {
			System.out.println("Starting trait command");
			EntityPlayer player = (EntityPlayer) sender;

			System.out.println("Trait: " + args[1]);

			if (args[0].equals("add")) {

				NetworkHandler.addTrait(args[1]);
			}else if (args[0].equals("remove")) {
				NetworkHandler.removeTrait(args[1]);
			}else {
				sender.sendMessage(new TextComponentString("Error: /trait <add/remove> <trait>"));

			}

		} else {
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