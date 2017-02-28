package org.b.v.blocks;

interface OppositeOrientation {
	public Orientation opposite();
}

enum Orientation implements OppositeOrientation{
	
	NORTH {
		public Orientation opposite() {
			return SOUTH;
		}
	},SOUTH {
		public Orientation opposite() {
		return SOUTH;
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