package org.b.v.blocks.protocol.test;

import java.util.LinkedHashMap;
import java.util.Map;

import org.b.v.blocks.core.Orientation;
import org.b.v.blocks.protocol.net.Message;
import org.b.v.blocks.protocol.net.MessageBox;
import org.b.v.blocks.protocol.net.local.LocalNode;

import com.google.gson.Gson;

public class FakeBlock implements LocalNode {
	

	private String ip;
	
	private FakeBlock east;
	private FakeBlock west;
	private FakeBlock north;
	private FakeBlock south;

	private FakeBlockRepo test;
	private final MessageBox fakeBus;
	
	public FakeBlock(MessageBox fakeBus,FakeBlockRepo test, String ip) {
		this.fakeBus = fakeBus;
		this.ip = ip;
		this.test=test;
	}

	private void blinkToNeighbour(FakeBlock block,Orientation orientation) {
		if(block != null) {
			block.neigbourBlinks(this);
		}
	}
	
	private Gson gson = new Gson();
	
	
	private class ReplyBuilder {
		private Map<String,Object> attributes;
		
		ReplyBuilder() {
			attributes = new LinkedHashMap<String,Object>();
		}
		
		ReplyBuilder attribute(String key,Object object) {
			this.attributes.put(key,object);
			return this;
		}
		
		String buildJson() {
			return gson.toJson(attributes);
		}
		
	}
	
	private void postMessage(String ip,String key,String message) {
		this.fakeBus.postMessage(ip,new ReplyBuilder().attribute(key, message).buildJson());
	}
	
	@Override
	public void receiveMessage(Message message) {
		if(message.getKey().contains("whoIsAlive")) {
			postMessage(ip,"Status","alive");
		} else if(message.getKey().contains("blinkNorth")) {
			blinkToNeighbour(north,Orientation.NORTH);
		} else if(message.getKey().contains("blinkSouth")) {
			blinkToNeighbour(south,Orientation.SOUTH);
		} else if(message.getKey().contains("blinkEast")) {
			blinkToNeighbour(east,Orientation.EAST);
		} else if(message.getKey().contains("blinkWest")) {
			blinkToNeighbour(west,Orientation.WEST);
		} else if((message.getKey().contains("rotate"))) {
			@SuppressWarnings("rawtypes")
			Map map = gson.fromJson(message.getKey(), Map.class);
			Number amount = (Number) map.get("amount");
			for(int i=0;i<amount.intValue();i++) {
				rotate();
			}
		}
	}

	private void neigbourBlinks(LocalNode block) { //moet FakeBlock zijn opdat hij kan de richting kan aangeven...
		Orientation orientation = null;
		if(block == this.east) {
			orientation = Orientation.EAST;
		} else if (block == this.west) {
			orientation = Orientation.WEST;
		} else if (block == this.north) {
			orientation = Orientation.NORTH;
		} else if (block == this.south) {
			orientation = Orientation.SOUTH;
		}
		postMessage(ip,"ledDetected",orientation.toString());
	}

	public void connectSouthOf(String string) {
		this.north = this.test.block(string);
		this.test.block(string).south = this;
	}
	
	public void connectNorthOf(String string) {
		this.south = this.test.block(string);
		this.test.block(string).north = this;
	}
	
	public void connectEastOf(String string) {
		this.west = this.test.block(string);
		this.test.block(string).east = this;
	}
	
	public void connectWestOf(String string) {
		this.east = this.test.block(string);
		this.test.block(string).west = this;
	}
	
	public void rotate() {
		FakeBlock previousNorth=this.north;
		this.north = this.west;
		this.west = this.south;
		this.south = this.east;
		this.east = previousNorth;
	}

	@Override
	public String getIp() {
		return this.ip;
	}
}