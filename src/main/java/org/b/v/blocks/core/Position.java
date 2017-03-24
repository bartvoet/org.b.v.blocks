package org.b.v.blocks.core;

public class Position {
	private int x, y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position to(Orientation orientation) {
		switch (orientation) {
		case EAST:
			return toEast();
		case WEST:
			return toWest();
		case NORTH:
			return toNorth();
		case SOUTH:
			return toSouth();
		}
		return this;
	}

	public Position toWest() {
		return new Position(x - 1, y);
	}

	public Position toNorth() {
		return new Position(x, y - 1);
	}

	public Position toEast() {
		return new Position(x + 1, y);
	}

	public Position toSouth() {
		return new Position(x, y + 1);
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	

}