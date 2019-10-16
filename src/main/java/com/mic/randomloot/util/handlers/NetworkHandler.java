package com.mic.randomloot.util.handlers;

import com.mic.randomloot.util.handlers.messages.BaseMessage;
import com.mic.randomloot.util.handlers.messages.MessageHandler;
import com.mic.randomloot.util.handlers.messages.StringMessage;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("randomlootforge");

	NetworkHandler(){
		INSTANCE.registerMessage(MessageHandler.class, BaseMessage.class, 0, Side.SERVER);
	}
	
	public static void getNewItem(){
		INSTANCE.sendToServer(new BaseMessage(1, "new_item"));
	}
	
	public static void reforge(){
		INSTANCE.sendToServer(new BaseMessage(0, "reforge"));
	}
	
	public static void addTrait(String trait){
		INSTANCE.sendToServer(new BaseMessage(2, trait));
	}
	
	public static void removeTrait(String trait){
		INSTANCE.sendToServer(new BaseMessage(3, trait));
	}

}

