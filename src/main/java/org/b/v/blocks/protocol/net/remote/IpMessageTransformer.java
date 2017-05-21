package org.b.v.blocks.protocol.net.remote;

import org.b.v.blocks.protocol.net.Message;
import org.b.v.blocks.protocol.net.remote.RemoteBus.MessageTransformer;

public class IpMessageTransformer implements MessageTransformer {

	private static final String BROADCAST_TOKEN = "broadcast";
	private static final String SEPARATOR = ";";
	
	@Override
	public Message beforeBroadcast(Message message) {
		return new Message("localhost",BROADCAST_TOKEN + SEPARATOR + message.getKey() );
	}

	@Override
	public Message beforeSending(Message message) {
		return new Message("localhost",message.getIp() + SEPARATOR + message.getKey() );
	}

	@Override
	public Message whenReceived(Message message) {
		String[] content = message.getKey().split(";");
		return new Message(content[0],content[1]);
	}

}
