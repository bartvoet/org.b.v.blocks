package org.b.v.blocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Blocks {
	
	private Matrix<Integer> matrix=new Matrix<Integer>();
	
	public void addBlock(int id,Orientation orientation,int otherId) {
		Position position = null;//TODO define the position and perform verificaitons
		
		matrix
			.setElementAt(id, position)
			.setElementAt(otherId, position.to(orientation));
				
//		Position idPos = this.findPosition(id);
//		Position otherIdPos = this.findPosition(otherId);
		
//		//if not first situation one of both should be null
//		if(idPos != null && otherIdPos != null) {
//			throw new IllegalArgumentException();
//		}
//
//		if(idPos == null && otherIdPos== null) {
//			throw new IllegalArgumentException();
//		}

		
		//swap so that idPos is not null
//		if(idPos==null) {
//			int tempId=id;
//			id=otherId;
//			otherId=tempId;
//			orientation=orientation.opposite();
//			idPos=otherIdPos;
//		}
//		
		
//		insertPosition(otherId,idPos.to(orientation));
		
		
		
		


		
	}
	


	public void drawBlocks(final BlocksScreen screen) {

	}
	
}
