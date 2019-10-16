package com.mic.randomloot.util.handlers.messages;

import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.IReforgeable;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StringMessageHandler implements IMessageHandler<StringMessage, IMessage> {
	// Do note that the default constructor is required, but implicitly defined
	// in this case

	@Override
	public IMessage onMessage(StringMessage message, MessageContext ctx) {

		System.out.println("Adding trait to Item");

		// This is the player the packet was sent to the server from
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

		serverPlayer.getServerWorld().addScheduledTask(() -> {
			ItemStack heldItem = serverPlayer.inventory.getCurrentItem();
			IReforgeable item = (IReforgeable) heldItem.getItem();

			TagHelper.addTag(heldItem, message.toSend.trim());

			// serverPlayer.inventory.getCurrentItem().shrink(1);
			item.setLore(heldItem, serverPlayer);
			serverPlayer.inventory.addItemStackToInventory(heldItem);
		});

		return null;

	}
}
