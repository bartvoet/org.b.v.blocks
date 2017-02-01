package org.b.v.blocks;

import java.util.Map;
import java.util.TreeMap;

public class BlockMatrix {

	private enum POS_RELATION { 
		LEFT,RIGHT,TOP,BOTTOM
	};
	private Map<Integer,Block> positions = new TreeMap<Integer,Block>();
//	private Set<BlockPosition> positions;
	
	private class Block {
		private int id;
		
		private Block toTheRight,toTheLeft,atTheBottom,atTheTop;
		
		Block(int id) {
			super();
			this.id = id;
		}

		void connectToBottomOf(Block position) {
			this.atTheTop = position;
			position.atTheBottom = position;
		}

		void connectToTopOf(Block position) {
			this.atTheBottom = position;
			position.atTheTop = position;
		}

		void connectToLeftOf(Block position) {
			
		}

		void connectToRightOf(Block position) {
			
		}
		
	}
	
	//principe => moeten allemaal geconnecteerd zijn, enkel een connectie
	//consistentie => alle blocks gaan hun connections nakijken adhv van positie-map
	
	private Block getById(int id) {
		if(this.positions.containsKey(id)) {
			return this.positions.get(id);
		}
		return null;
	}
	

	public void register(int block) {
		//if empty this is starting point until one more ...
		//stating-point is not needed if you work with neighbour-system
		//one block request all it's neighbours to draw ...
		//original node-based idea might be the simplest...
		//kind of transaction when you want to verify ...
	}
	
	public void connect(int from, int to, POS_RELATION relation) {
		//top-rigt is the start-po
		//getById(from);
		//getById(to)
		//
	}
	


	
	//connect(1,2).toTheLeft() throws InconsistencyException
	//intern matrix bijhouden van posities
	//block heeft een aantal properties die worden upgedate en verzonden bij update
	//wel scheiding houden van drawer (geen kennis van Swing daarbinnen)
	//...
	
}
