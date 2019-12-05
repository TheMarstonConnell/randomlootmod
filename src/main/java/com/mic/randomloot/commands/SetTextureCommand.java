package com.mic.randomloot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.handlers.NetworkHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class SetTextureCommand implements ICommand {
	private final List<String> aliases;

	public SetTextureCommand() {
		aliases = new ArrayList<String>();
		aliases.add("texture");

	}

	public String getCommandName() {
		return "texture";
	}

	public String getCommandUsage(ICommandSender var1) {
		return "texture <texture_number>";
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
		return "texture";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "texture <texture_number>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (sender instanceof EntityPlayer) {
			// System.out.println("Starting trait command");
			EntityPlayer player = (EntityPlayer) sender;

			// System.out.println("Trait: " + args[1]);

			NetworkHandler.setTexture(args[0]);

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
		return null;

	}
}