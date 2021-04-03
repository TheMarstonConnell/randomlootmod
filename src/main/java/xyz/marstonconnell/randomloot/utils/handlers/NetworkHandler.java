package xyz.marstonconnell.randomloot.utils.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.marstonconnell.randomloot.RandomLootMod;

public class NetworkHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(RandomLootMod.MODID, "main"), 
			() -> PROTOCOL_VERSION, 
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	public static void registerMessage() {
		INSTANCE.registerMessage(0, BaseMessage.class, BaseMessage::encode, BaseMessage::new, BaseMessage::handle);
	}

	public static void getNewItem(int rarity) {
		INSTANCE.sendToServer(new BaseMessage(0, rarity));

	}

}
