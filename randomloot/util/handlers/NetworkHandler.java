package com.mic.randomloot.util.handlers;

import com.mic.randomloot.util.handlers.messages.ItemMessage;
import com.mic.randomloot.util.handlers.messages.MessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("randomloot");

	NetworkHandler(){
		INSTANCE.registerMessage(MessageHandler.class, ItemMessage.class, 0, Side.SERVER);
	}
	
	public static void getNewItem(){
		INSTANCE.sendToServer(new ItemMessage(1));
	}

}

