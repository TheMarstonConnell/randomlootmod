package com.mic.randomloot.util.handlers;

import com.mic.randomloot.util.handlers.messages.StringMessage;
import com.mic.randomloot.util.handlers.messages.StringMessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class StringNetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("randomlootstrings");

	StringNetworkHandler(){
		INSTANCE.registerMessage(StringMessageHandler.class, StringMessage.class, 5, Side.SERVER);
	}
	
	public static void addTrait(String trait){
		INSTANCE.sendToServer(new StringMessage(trait));
	}

}

