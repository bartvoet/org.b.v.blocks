package org.b.v.blocks;

public class BlockMatrix {
	


	private Block firstBlockRelation=new Block(0);
	
	public void addBlock(int id,Orientation orientation,int otherId) {
		firstBlockRelation.posBlock(id, orientation, otherId);
	}
	
	public void drawBlocks(final BlocksScreen screen) {
		firstBlockRelation.drawNeighbours(screen,new Position(0,0));
	}
	
}
