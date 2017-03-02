package org.b.v.blocks;

public class Blocks {
	
	private Matrix<Integer> matrix=new Matrix<Integer>();
	
	public Blocks addBlock(int id,Orientation orientation,int otherId) {
		Position position = null;//TODO define the position and perform verificaitons
		int from,to;
		
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
				//TODO: verify for correctness...
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
	

	public void drawBlocks(final BlocksScreen screen) {
//		this.matrix.runthrough
	}

	@Override
	public String toString() {
		return this.matrix.toString();
	}
}
