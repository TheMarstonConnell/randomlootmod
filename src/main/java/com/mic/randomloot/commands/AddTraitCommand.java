package com.mic.randomloot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mic.randomloot.tags.TagHelper;
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
		return "trait <add/remove> <trait>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (sender instanceof EntityPlayer) {
//			System.out.println("Starting trait command");
			EntityPlayer player = (EntityPlayer) sender;

//			System.out.println("Trait: " + args[1]);

			if (args[0].equals("add")) {

//				NetworkHandler.addTrait(args[1]);
				ItemStack heldItem = player.inventory.getCurrentItem();
				IReforgeable item = (IReforgeable) heldItem.getItem();

				TagHelper.addTag(heldItem, args[1].trim());

				// serverPlayer.inventory.getCurrentItem().shrink(1);
				item.setLore(heldItem, player);
				item.setName(heldItem);
			}else if (args[0].equals("remove")) {
//				NetworkHandler.removeTrait(args[1]);
				ItemStack heldItem = player.inventory.getCurrentItem();
				IReforgeable item = (IReforgeable) heldItem.getItem();

				TagHelper.removeTag(heldItem, args[1].trim());

				// serverPlayer.inventory.getCurrentItem().shrink(1);
				item.setLore(heldItem, player);
				item.setName(heldItem);
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
		if (args.length == 1)
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, new String[] {"add", "remove"});
        }
        else
        {
        	
            return args.length == 2 ? CommandBase.getListOfStringsMatchingLastWord(args, TagHelper.tagNames) : Collections.emptyList();
        }
		
	}
}