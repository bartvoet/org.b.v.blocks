package org.b.v.blocks.core;

import java.util.Map;

import org.b.v.blocks.screen.BlockPainter;

public class Blocks {
	
	private Matrix<String> matrix=new Matrix<String>();
	
	public Blocks addBlockRelationShip(String id,Orientation orientation,String otherId) {
		Position position = null;//TODO define the position and perform verificaitons
		String from,to;
		
		if(this.matrix.isEmpty()) {
			position = new Position(0,0);
			from=id;
			to=otherId;
		} else {
			Position idPos = this.matrix.lookForFirstOccurence(id);
			Position otherPos = this.matrix.lookForFirstOccurence(otherId);
			
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
		
		this.matrix
			.setElementAt(from, position)
			.setElementAt(to, position.to(orientation));
				
		return this;
	}
	

	//TODO support for removing...?
	
	public void drawBlocks(final BlockPainter screen) {
		//TODO maybe indicate that new screen ...
		for(Map.Entry<Position,String> entry:this.matrix.getAllPositions().entrySet()) {
			screen.drawBlockAtPosition(entry.getValue(),entry.getKey());
		}
	}

	@Override
	public String toString() {
		return this.matrix.toString();
	}
}
