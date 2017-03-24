package org.b.v.blocks.core;

interface OppositeOrientation {
	public Orientation opposite();
}

public enum Orientation implements OppositeOrientation{
	
	NORTH {
		public Orientation opposite() {
			return SOUTH;
		}
	},SOUTH {
		public Orientation opposite() {
			return NORTH;
		}
	}
	,EAST {
		public Orientation opposite() {
			return WEST;
		}
	},WEST {
		public Orientation opposite() {
			return EAST;
		}
	};
//
//	public Orientation opposite() {
//		return null;
//	}
}