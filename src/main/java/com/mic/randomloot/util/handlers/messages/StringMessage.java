package com.mic.randomloot.util.handlers.messages;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class StringMessage implements IMessage {

	// A default constructor is always required

		String toSend;
//		int byteLength = 0;

		public StringMessage(String toSend) {
			this.toSend = toSend;
//			byteLength = toSend.getBytes().length;
		}

		@Override
		public void toBytes(ByteBuf buf) {
			// Writes the int into the buf
			
//			PacketBuffer myBuf = new PacketBuffer(buf);
//			myBuf.writeString(toSend);
			// or
			
//			buf.writeBytes(toSend.getBytes());
	        ByteBufUtils.writeUTF8String(buf, toSend);
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			// Reads the int back from the buf. Note that if you have multiple values, you
			// must read in the same order you wrote.
//			PacketBuffer myBuf = new PacketBuffer(buf);
//			toSend = myBuf.readString(20);
			
	        ByteBufUtils.readUTF8String(buf);


		}
}
