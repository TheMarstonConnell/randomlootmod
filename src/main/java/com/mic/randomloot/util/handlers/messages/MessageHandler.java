package com.mic.randomloot.util.handlers.messages;

import com.mic.randomloot.items.CaseItem;
import com.mic.randomloot.items.SwordItem;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.IRandomTool;
import com.mic.randomloot.util.IReforgeable;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler implements IMessageHandler<BaseMessage, IMessage> {
	// Do note that the default constructor is required, but implicitly defined
	// in this case

	@Override
	public IMessage onMessage(BaseMessage message, MessageContext ctx) {
		if (message.toSend == 1) {
//			System.out.println("Giving Item");

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			serverPlayer.getServerWorld().addScheduledTask(() -> {
				Item i = serverPlayer.inventory.getCurrentItem().getItem();
				serverPlayer.inventory.getCurrentItem().shrink(1);

				serverPlayer.inventory
				.addItemStackToInventory(CaseItem.getItem(serverPlayer.getServerWorld(), serverPlayer, i));
			});

			// serverPlayer.inventory.currentItem,

			// No response packet
			return null;
		} else if (message.toSend == 0) {

//			System.out.println("Reforging Item");

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

			serverPlayer.getServerWorld().addScheduledTask(() -> {
				ItemStack heldItem = serverPlayer.inventory.getCurrentItem();
				heldItem = TagHelper.removeAllTags(heldItem);
				IReforgeable item = (IReforgeable) heldItem.getItem();
				
				ItemStack newItem = item.reforge(heldItem);
				item.setLore(newItem, serverPlayer);

				newItem = item.setName(newItem);
				
				ItemStack offHand = serverPlayer.getHeldItem(EnumHand.OFF_HAND);
				offHand.shrink(12);


				//				serverPlayer.inventory.getCurrentItem().shrink(1);
				serverPlayer.inventory.addItemStackToInventory(newItem);
			});

			return null;
		} else if (message.toSend == 2) {
//			System.out.println("Adding trait to Item");

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

			serverPlayer.getServerWorld().addScheduledTask(() -> {
				ItemStack heldItem = serverPlayer.inventory.getCurrentItem();
				IReforgeable item = (IReforgeable) heldItem.getItem();

				TagHelper.addTag(heldItem, message.strSend.trim());

				// serverPlayer.inventory.getCurrentItem().shrink(1);
				item.setLore(heldItem, serverPlayer);
				item.setName(heldItem);
//				serverPlayer.inventory.addItemStackToInventory(heldItem);
				
			});

			return null;
		} else if (message.toSend == 3) {
//			System.out.println("Taking trait from Item");

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

			serverPlayer.getServerWorld().addScheduledTask(() -> {
				ItemStack heldItem = serverPlayer.inventory.getCurrentItem();
				IReforgeable item = (IReforgeable) heldItem.getItem();

				TagHelper.removeTag(heldItem, message.strSend.trim());

				// serverPlayer.inventory.getCurrentItem().shrink(1);
				item.setLore(heldItem, serverPlayer);
				item.setName(heldItem);
			});

			return null;
		} else if (message.toSend == 4) {

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

			serverPlayer.getServerWorld().addScheduledTask(() -> {
				ItemStack heldItem = serverPlayer.inventory.getCurrentItem();
				IReforgeable item = (IReforgeable) heldItem.getItem();
				IRandomTool randTool = (IRandomTool) heldItem.getItem();
				
				int texture = 1;
				
				
				try {
					texture = Integer.valueOf(message.strSend.trim());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				randTool.chooseTexture(heldItem, texture);

				// serverPlayer.inventory.getCurrentItem().shrink(1);
				item.setLore(heldItem, serverPlayer);
				item.setName(heldItem);
			});

			return null;
		}else {
		}
		return null;
	}


}
