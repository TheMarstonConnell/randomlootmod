package com.mic.randomloot.util.handlers.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BaseMessage implements IMessage {
	  // A default constructor is always required
	  public BaseMessage(){}

	  int toSend;
	  String strSend;
	  public BaseMessage(int toSend, String strSend) {
	    this.toSend = toSend;
	    this.strSend = strSend;
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeInt(toSend);
        ByteBufUtils.writeUTF8String(buf, strSend);

	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
	    toSend = buf.readInt();
	    strSend = ByteBufUtils.readUTF8String(buf);
	  }
	}

