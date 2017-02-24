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
		if (this.id == 0) {
			if (this.east != null) {
				this.east.posBlock(id, orientation, otherId);
			} else {
				this.east = new Block(id);
				this.east = this.east.posBlock(id, orientation, otherId);
			}
		}

		if (this.id == otherId) {

			if (orientation == Orientation.EAST) {
				this.east = new Block(id);
			}

			if (orientation == Orientation.SOUTH) {
				this.south = new Block(id);
			}

			if (orientation == Orientation.WEST) {
				Block newBlock = new Block(id);
				newBlock.east = this;
				return newBlock;
			}

			if (orientation == Orientation.NORTH) {
				Block newBlock = new Block(id);
				newBlock.south = this;
				return newBlock;
			}

			return null;
		} else {
			if (this.south != null) {
				this.south = this.south.posBlock(id, orientation, otherId);
			}
			if (this.east != null) {
				this.east = this.east.posBlock(id, orientation, otherId);
			}
			return this;
		}
	}

	public void drawNeighbours(BlockPainter painter) {
		//positie bijhouden/meegeven???
		//of gewoon block teruggeven
		
		
		 if(east!=null) {
			 painter.drawEast(this.east);
		 }
		
		 if(south!=null) {
			 painter.drawSouth(this.south);
		 }

	}

}
