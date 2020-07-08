package xyz.marstonconnell.randomloot.utils.handlers;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.marstonconnell.randomloot.RandomLootMod;
import xyz.marstonconnell.randomloot.init.ItemFactory;
import xyz.marstonconnell.randomloot.init.ItemUtils;
import xyz.marstonconnell.randomloot.init.RLItems;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.utils.WeightedChooser;

import java.util.function.Supplier;

public class BaseMessage {
	private int value;
	private int rarity;

	public BaseMessage() {
		
	}
	
	public BaseMessage(int i, int rarity) {
		this.value = i;
		this.rarity = rarity;
	}

	public BaseMessage(PacketBuffer buff) {
		this.value = buff.readInt();
		this.rarity = buff.readInt();
	}

	public void encode(PacketBuffer buff) {
		buff.writeInt(value);
		buff.writeInt(rarity);

	}

	public static void handle(BaseMessage pckt, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			if (pckt.value == 0) {
				ctx.get().getSender().getHeldItemMainhand().shrink(1);

				getNewItem(pckt.rarity, ctx.get().getSender());

			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static ItemStack getNewItem(int rarity, ServerPlayerEntity playerIn) {

		

		
		ItemStack s = new ItemStack(RandomLootMod.wc.getRandomObject());
		
		 s = ItemFactory.forgeItem(s, rarity);
		
				
		playerIn.addItemStackToInventory(s);

		return s;

	}

}
