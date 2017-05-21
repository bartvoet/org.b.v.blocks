package org.b.v.blocks.protocol.net.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.b.v.blocks.protocol.net.Bus;
import org.b.v.blocks.protocol.net.Filter;
import org.b.v.blocks.protocol.net.Message;
import org.b.v.blocks.protocol.net.MessageBox;

public class LocalBus implements Bus,MessageBox {

	private Map<String,LocalNode> nodes = new TreeMap<String,LocalNode>();
	private List<Message> messages = new ArrayList<Message>();
	private String localIP;

	public LocalBus(String ip) {
		this.localIP = ip;
	}
	
	public void addNode(LocalNode node) {
		this.nodes.put(node.getIp(), node);
	}
		
	public void postMessage(String ip,String key) {
		this.messages.add(new Message(ip,key));
	}
	
	@Override
	public void broadcast(String request) {
		System.out.println("broadcast =>" + request);
		for(LocalNode block : nodes.values()) {
			block.receiveMessage(new Message(this.localIP,request));
		}
	}

	@Override
	public void sendMessage(String ip, String key) {
		System.out.println(ip + " => " + key);
		this.nodes.get(ip).receiveMessage(new Message(this.localIP,key));
	}

	@Override
	public List<Message> waitForMessages(int i, TimeUnit seconds, Filter... filters) {
		List<Message> copy = new ArrayList<Message>();
		for(Message message:messages) {
			System.out.println(message.getKey() + " <= " + message.getIp());
			copy.add(message);
		}
		messages.clear();
		return copy;
	}

	@Override
	public Message waitForSingleMessage(int i, TimeUnit seconds, Filter startsWith) {
		Message message = null;
		if(!messages.isEmpty()) {
			message = messages.get(0);
			System.out.println(message.getKey() + " <= " + message.getIp());
		}
		messages.clear();
		return message;
	}

}
