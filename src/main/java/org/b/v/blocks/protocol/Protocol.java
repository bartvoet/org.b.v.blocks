package org.b.v.blocks.protocol;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.b.v.blocks.core.Matrix;
import org.b.v.blocks.core.Orientation;
import org.b.v.blocks.core.Position;
import org.b.v.blocks.protocol.net.Bus;

public class Protocol {
	
	//TODO implement hook-system for local network-acces-testing...
	private Matrix<String> matrix = new Matrix<String>();
	private Set<String> ids = new LinkedHashSet<String>();
	private ProtocolMessages messages;
	
	public Protocol(Bus bus) {
		this.messages = new ProtocolMessages(bus);
	}

	public Matrix<String> run() {
		ids = messages.detectBlocks();
		setBlocksInReadModus();
		discoverNeighbours();
		disableReadModus();
		return this.matrix;
	}
	
	private void addBlockRelationShip(String id,Orientation orientation,String otherId) {
		Position position = null;//TODO define the position and perform verificaitons
		String from,to;
		
		if(matrix.isEmpty()) {
			position = new Position(0,0);
			from=id;
			to=otherId;
		} else {
			Position idPos = matrix.lookForFirstOccurence(id);
			Position otherPos = matrix.lookForFirstOccurence(otherId);
			
			if(idPos == null && otherPos == null) {
				throw new IllegalArgumentException("At least one of both...");
			}
			
			if(idPos != null && otherPos != null) {
				//TODO: verify for correctness...by checking the orientation...
				
			}
			
			//swap
			position = idPos == null ? otherPos:idPos;
			from = idPos == null ? otherId:id;
			to = idPos != null ? otherId:id;
			orientation = idPos==null ? orientation.opposite():orientation;
		}
		
		matrix
			.setElementAt(from, position)
			.setElementAt(to, position.to(orientation));
				
	}
	
	private void discoverNeighbours() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String id : ids) {
			for(Orientation orientation : Orientation.values()) {
				ProtocolMessages.LedDetection detection = null;
				do{
					detection = messages.waitForLedDetection(id, orientation);
//					if(detection == null) {
//						System.out.println("detection is null??");
//					}
					if(detection != null) {
						if(!orientation.isOpposite(detection.getCounterOrientation())) {
							messages.rotate(detection.getId(),  //id, 
									detection.getCounterOrientation()
									.calculateRotation(
											orientation.opposite()
											)
										);
							
							
						} else {
							addBlockRelationShip(id, orientation, detection.getId());
						}
					} 
					
				} while(detection !=null && !orientation.isOpposite(detection.getCounterOrientation()));
			}
		}
		
	}

	private void setBlocksInReadModus() {
		for(String id : ids) {
			messages.setBlockInReadModus(id);
		}
	}
	
	private void disableReadModus() {
		for(String id : ids) {
			messages.disableReadModus(id);
		}
	}
	
}


