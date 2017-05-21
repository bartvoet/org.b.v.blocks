package org.b.v.blocks.core;


interface OppositeOrientation {
	public Orientation opposite();
}

public enum Orientation implements OppositeOrientation{
	
	NORTH {
		public Orientation opposite() {
			return SOUTH;
		}
	},EAST {
		public Orientation opposite() {
			return WEST;
		}
	},SOUTH {
		public Orientation opposite() {
			return NORTH;
		}
	},WEST {
		public Orientation opposite() {
			return EAST;
		}
	};

	public boolean isOpposite(Orientation orientation) {
		if(orientation == null) {
			return false;
		}
		return this == orientation.opposite();
	}

	private static int NUMBER_OF_ORIENTATIONS = Orientation.values().length;
	
	public int calculateRotation(Orientation counterOrientation) {
//		return Math.abs(this.ordinal() - counterOrientation.ordinal());
		return (NUMBER_OF_ORIENTATIONS + (counterOrientation.ordinal() - this.ordinal())) % NUMBER_OF_ORIENTATIONS; 
//		return (NUMBER_OF_ORIENTATIONS + (this.ordinal() - counterOrientation.ordinal())) % NUMBER_OF_ORIENTATIONS;
		
	}
}