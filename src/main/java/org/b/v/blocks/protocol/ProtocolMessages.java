package org.b.v.blocks.protocol;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.b.v.blocks.core.Orientation;
import org.b.v.blocks.protocol.net.Bus;
import org.b.v.blocks.protocol.net.Filter;
import org.b.v.blocks.protocol.net.Filters;
import org.b.v.blocks.protocol.net.Message;

import com.google.gson.Gson;

public class ProtocolMessages {
	final static Map<Orientation,String> directionTaskNames = new LinkedHashMap<Orientation,String>();

	public static class LedDetection {
		private String id;
		private Orientation counterOrientation;

		public LedDetection(String id,Orientation counterOrientation) {
			this.id=id;
			this.counterOrientation=counterOrientation;
		}
		
		public String getId() {
			return id;
		}

		public Orientation getCounterOrientation() {
			return counterOrientation;
		}
	}
	
	private static Gson gson = new Gson();

	private class TaskBuilder {
		private Map<String,Object> attributes;
		
		TaskBuilder(String name) {
			attributes = new LinkedHashMap<String,Object>();
			this.attributes.put("Taak", name);
		}
		
		TaskBuilder attribute(String key,Object object) {
			this.attributes.put(key,object);
			return this;
		}
		
		String buildJson() {
			return gson.toJson(attributes);
		}
		
	}
	
	static { 
		directionTaskNames.put(Orientation.NORTH,"findNeighbourN");
		directionTaskNames.put(Orientation.SOUTH,"findNeighbourS");
		directionTaskNames.put(Orientation.EAST,"findNeighbourO");
		directionTaskNames.put(Orientation.WEST,"findNeighbourW");
	}

	private Bus bus;

	public ProtocolMessages(Bus bus) {
		this.bus = bus;
	}
	
	public void rotate(String adress,int numberOfRotations) {
		bus.sendMessage(adress,
				new TaskBuilder("rotate")
					.attribute("amount",numberOfRotations)
					.attribute("richting","links")
					.buildJson()
					);
	}
	
	private static Map<String,Orientation> orientationReturns = new LinkedHashMap<String,Orientation>();
	static {
		orientationReturns.put("noord",Orientation.NORTH);
		orientationReturns.put("zuid",Orientation.SOUTH);
		orientationReturns.put("oost",Orientation.EAST);
		orientationReturns.put("west",Orientation.WEST);
		
	}
	
	public LedDetection waitForLedDetection(String id,Orientation orientation) {
		bus.sendMessage(id, taskMessage(directionTaskNames.get(orientation)));
//		Message message = bus.waitForSingleMessage(1000,TimeUnit.MILLISECONDS,Filters.containsKey("ledDetected"));
		Message message = bus.waitForSingleMessage(1000,TimeUnit.MILLISECONDS,new Filter(){
			@Override
			public boolean applies(Message message) {
				if("noord".equals(message.getKey()) 
					||
					"zuid".equals(message.getKey())
					||
					"oost".equals(message.getKey())
					||
					"west".equals(message.getKey()) ){
					return true;
				}
				return false;
			}});
		if(message!=null) {
//			@SuppressWarnings("rawtypes")
//			Map result = gson.fromJson(message.getKey(), Map.class);
//			return new LedDetection(message.getIp(),Orientation.valueOf((String)result.get("ledDetected")));
			return new LedDetection(message.getIp(),orientationReturns.get(message.getKey()));
		}
		return null;
	}
	
	public Set<String> detectBlocks() {
		Set<String> ids=new LinkedHashSet<String>();
		bus.broadcast(taskMessage("whoIsAlive"));
		for(Message response : bus.waitForMessages(1000,TimeUnit.MILLISECONDS,Filters.containsKey("alive"))) {
			ids.add(response.getIp());
		}
		return ids;
	}
	
	public void setBlockInReadModus(String id) {
		bus.sendMessage(id, taskMessage("readPos"));
	}
	
	public void disableReadModus(String id) {
		bus.sendMessage(id, taskMessage("stopReadPos"));
	}
	
	public void vibrate(String id) {
		bus.sendMessage(id, new  TaskBuilder("tril").attribute("trilNiveau", 100).buildJson());
	}
	
	public void stopVibrate(String id) {
		bus.sendMessage(id, new  TaskBuilder("endTril").buildJson());
	}
	
	private String taskMessage(String name) {
		return new TaskBuilder(name).buildJson();
		
	}

}
