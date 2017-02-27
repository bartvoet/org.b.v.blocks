package org.b.v.blocks;

import java.util.ArrayList;
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
	
	
	private List<List<B>> matrix=new ArrayList<List<B>>();
	
	
	public Position findPosition(Integer id) {
		int x=0;
		for(List<B> bs : this.matrix ) {
			int y=0;
			for(B b : bs) {
				if(b.hasId(id)) {
					return new Position(x,y);
				}
				y++;
			}
			x++;
		}
		
		return null;
	}
	
	public void addBlock(int id,Orientation orientation,int otherId) {
		if(orientation==Orientation.NORTH || orientation==Orientation.WEST) {
			//switch
		}
		
		if(this.isEmpty()) {
		
		}
		
		
		Position idPos = this.findPosition(id);
		Position otherIdPos = this.findPosition(otherId);
		
		if(idPos != null && otherIdPos != null) {
			
		}
		
		if(idPos != null) {
			
		}

		if(otherIdPos != null) {
			
		}
		
		

		
	}
	
	private boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void drawBlocks(final BlocksScreen screen) {

	}
	
}
