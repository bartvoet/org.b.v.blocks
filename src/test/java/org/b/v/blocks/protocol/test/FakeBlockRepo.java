package org.b.v.blocks.protocol.test;

import java.util.Map;
import java.util.TreeMap;

import org.b.v.blocks.protocol.net.MessageBox;
import org.b.v.blocks.protocol.net.local.LocalNode;

public class FakeBlockRepo {
	private Map<String,FakeBlock> blocks=new TreeMap<String,FakeBlock>();
	private MessageBox bus;
	
	public FakeBlockRepo(MessageBox bus) {
		this.bus = bus;
	}
	
	public FakeBlock block(String ip) {
		if(!this.blocks.containsKey(ip)) {
			this.blocks.put(ip, new FakeBlock(bus,this, ip));
		}
		return this.blocks.get(ip);
	}

	public Iterable<? extends LocalNode> allBlocks() {
		return blocks.values();
	}

}
