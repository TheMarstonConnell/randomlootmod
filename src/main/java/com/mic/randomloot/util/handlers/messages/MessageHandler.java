package com.mic.randomloot.util.handlers.messages;

import com.mic.randomloot.items.CaseItem;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler implements IMessageHandler<ItemMessage, IMessage> {
	// Do note that the default constructor is required, but implicitly defined
	// in this case

	@Override
	public IMessage onMessage(ItemMessage message, MessageContext ctx) {
		
		System.out.println("Giving Item");
		
		// This is the player the packet was sent to the server from
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		// The value that was sent
		int amount = message.toSend;
		// Execute the action on the main server thread by adding it as a
		// scheduled task
		serverPlayer.getServerWorld().addScheduledTask(() -> {
			Item i = serverPlayer.inventory.getCurrentItem().getItem();
			serverPlayer.inventory.getCurrentItem().shrink(1);

			serverPlayer.inventory
					.addItemStackToInventory(CaseItem.getItem(serverPlayer.getServerWorld(), serverPlayer, i));
		});

		// serverPlayer.inventory.currentItem,

		// No response packet
		return null;
	}
}
