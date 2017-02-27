package org.b.v.blocks;
public class Position {
	private int x,y;
	Position(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public Position toEast() {
		return new Position(x+1,y);
	}
	
	public Position toSouth() {
		return new Position(x,y+1);
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
}