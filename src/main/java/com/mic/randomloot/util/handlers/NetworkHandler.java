package com.mic.randomloot.util.handlers;

import com.mic.randomloot.util.handlers.messages.BaseMessage;
import com.mic.randomloot.util.handlers.messages.MessageHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("randomloot");

	NetworkHandler(){
		INSTANCE.registerMessage(MessageHandler.class, BaseMessage.class, 0, Side.SERVER);
	}
	
	public static void getNewItem(){
		INSTANCE.sendToServer(new BaseMessage(1));
	}
	
	public static void reforge(){
		INSTANCE.sendToServer(new BaseMessage(0));
	}

}

