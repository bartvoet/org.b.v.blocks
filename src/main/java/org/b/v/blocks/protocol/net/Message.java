package org.b.v.blocks.protocol.net;

public class Message {

	private String ip;
	private String key;
	
	public Message(String ip, String key) {
		this.ip = ip;
		this.key=key;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getKey() {
		return this.key;
	}
}
