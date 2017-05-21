package org.b.v.blocks.protocol.net.local;

import org.b.v.blocks.protocol.net.Message;

public interface LocalNode {

	void receiveMessage(Message message);

	String getIp();
	
}