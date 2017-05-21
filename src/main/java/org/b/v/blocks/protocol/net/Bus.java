package org.b.v.blocks.protocol.net;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface Bus {

	void broadcast(String task);
	void sendMessage(String netwerkAdress,String task);
	List<Message> waitForMessages(int number, TimeUnit unit,Filter...filters);
	Message waitForSingleMessage(int number, TimeUnit unit, Filter startsWith);
	
}
