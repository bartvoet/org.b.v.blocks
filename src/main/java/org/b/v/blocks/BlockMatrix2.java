package org.b.v.blocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockMatrix2 {
	
	private static class B {
		private Integer id;
		private boolean filled;
		
		private B(){
			this.filled=false;
		}
		
		public B(Integer id) {
			this.filled=false;
			this.id=id;
		}
		
		private static B emptyBlock() {
			return new B();
		}

		public boolean hasId(Integer id2) {
			if(filled) {
				return this.id.equals(id2);
			}
			
			return false;
		}
	}
	
	
	private LinkedList<LinkedList<B>> matrix=new LinkedList<LinkedList<B>>();
	
	
	public Position findPosition(Integer id) {
		int x=0;
		for(List<B> bs : this.matrix ) {
			int y=0;
			for(B b : bs) {
				if(b!=null && b.hasId(id)) {
					return new Position(x,y);
				}
				y++;
			}
			x++;
		}
		
		return null;
	}
	
	private void makeRoom() {
		
	}
	
	private boolean isMostSouth() {
		return false;
	}
	
	private boolean isMostNorth() {
		return false;
	}
	
	
	
	private List<B> insertInRow(List<B> row,Position position,B bl) {
		List<B> newRow = new ArrayList<B>();
		int counter=0;
		for(B b:row) {
			if(counter==position.getX()) {
				newRow.add(bl);
			} else {
				newRow.add(b);
			}
			counter++;
		}
		return newRow;
	}
	
	private void moveBlocksToSouthFrom(Position position) {
		
	}
	
	public LinkedList<B> addNewRowAtStart() {
		LinkedList<B> newList = new LinkedList<B>();
		this.matrix.addLast(newList);
		return newList;
	}
	
	public LinkedList<B> addNewRowAtEnd() {
		LinkedList<B> newList = new LinkedList<B>();
		this.matrix.addFirst(newList);
		return newList;
	}

	
	public void addBlock(int id,Orientation orientation,int otherId) {
	
		if(this.isEmpty()) {
			LinkedList<B> b = new LinkedList<B>();
			b.add(new B(id));
			switch(orientation) {
				case EAST:b.addLast(new B(otherId));break;
				case WEST:b.addFirst(new B(otherId));break;
				case SOUTH:addNewRowAtEnd().add(new B(otherId));break;
				case NORTH:addNewRowAtStart().add(new B(otherId));break;
			}
			return;
		} 
		
		
		Position idPos = this.findPosition(id);
		Position otherIdPos = this.findPosition(otherId);
		
		
		if(idPos != null && otherIdPos != null) {
			throw new IllegalArgumentException();
		}

		if(idPos == null && otherIdPos== null) {
			throw new IllegalArgumentException();
		}

		
		//swap so that idPos is not null
		if(idPos==null) {
			int tempId=id;
			id=otherId;
			otherId=tempId;
			orientation=orientation.opposite();
			idPos=otherIdPos;
		}
		
		
		insertPosition(otherId,idPos.to(orientation));
		
		
		
		
		//lege plekken
		//east of west op zelfde rij
		//bestaat de rij al
		
		
//		if(idPos != null) {
//			switch(orientation) {
//				case EAST:b.addLast(new B(otherId));break;
//				case WEST:b.addFirst(new B(otherId));break;
//				case SOUTH:addNewRowAtEnd().add(new B(otherId));break;
//				case NORTH:addNewRowAtStart().add(new B(otherId));break;
//			}
//		}
//
//		if(otherIdPos != null) {
//			
//		}
//		
//		return null;

		
	}
	
	private void insertPosition(int otherId, Position position) {
		// TODO Auto-generated method stub
		
	}

	private boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void drawBlocks(final BlocksScreen screen) {

	}
	
}
