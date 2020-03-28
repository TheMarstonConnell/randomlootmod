package com.mic.randomloot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.IReforgeable;
import com.mic.randomloot.util.handlers.NetworkHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

//			NetworkHandler.setTexture(args[0]);
			
			ItemStack heldItem = player.inventory.getCurrentItem();
			IReforgeable item = (IReforgeable) heldItem.getItem();
			IRandomTool randTool = (IRandomTool) heldItem.getItem();
			
			int texture = 1;
			
			
			try {
				texture = Integer.valueOf(args[1].trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			heldItem = randTool.chooseTexture(heldItem, texture);

			// serverPlayer.inventory.getCurrentItem().shrink(1);
			item.setLore(heldItem, player);
			item.setName(heldItem);
			
			sender.sendMessage(new TextComponentString("Texture changed."));

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