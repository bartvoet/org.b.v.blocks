package org.b.v.blocks;

public class Block {
	private int id;
	private Block south, east;

	public Block(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public Block posBlock(int id, Orientation orientation, int otherId) {
		//temp solution
		if (this.id == 0) {
			if (this.east != null) {
				this.east.posBlock(id, orientation, otherId);
			} else {
				this.east = new Block(id);
				this.east = this.east.posBlock(id, orientation, otherId);
			}
			return this;
		}

		//swith-around...
		if(orientation == Orientation.WEST) {
			this.posBlock(otherId, Orientation.EAST, id);
		}
		
		if(orientation == Orientation.NORTH) {
			this.posBlock(otherId, Orientation.SOUTH, id);
		}
		

		if(this.id==id) {
			if (orientation == Orientation.EAST) {
				Block newBlock = new Block(otherId);
				if(this.east==null) {
					this.east = newBlock;
				} else {
					newBlock.east=this.east;
					this.east=newBlock;
				}
			}
			if (orientation == Orientation.SOUTH) {
				Block newBlock = new Block(otherId);
				if(this.south==null) {
					this.south = newBlock;
				} else {
					newBlock.south=this.south;
					this.south=newBlock;
				}
			}
			return this;
		}
		
		if (this.id == otherId) {
			Block newBlock = new Block(id);
			if (orientation == Orientation.EAST) {
				newBlock.east=this;
			} else {
				newBlock.south=this;
			}
			return newBlock;
		}
		
		if (this.south != null) {
			this.south = this.south.posBlock(id, orientation, otherId);
		}
		if (this.east != null) {
			this.east = this.east.posBlock(id, orientation, otherId);
		}
		
		return this;
	}

	public void drawNeighbours(BlockPainter painter, Position position) {
		if(this.id==0) {
			 if(east!=null) {
				 painter.drawBlockAtPosition(east.id, position);
				 east.drawNeighbours(painter,position);
			 }
		}
		
		 if(east!=null) {
			 painter.drawBlockAtPosition(east.id, position.toEast());
			 east.drawNeighbours(painter,position.toEast());
		 }
		
		 if(south!=null) {
			 painter.drawBlockAtPosition(south.id, position.toSouth());
			 south.drawNeighbours(painter,position.toSouth());
		 }
	}

}
