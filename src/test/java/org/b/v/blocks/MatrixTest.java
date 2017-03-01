package org.b.v.blocks;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {


	@Test
	public void blabla() {
		Matrix<String> matrix = new Matrix<String>()
			.setElementAt("hello", 0,0)
			.setElementAt("world", 0,1)
			.setElementAt("!!", 1,1);
		
		assertEquals("hello",matrix.getElementAt(0, 0));
		assertEquals("world",matrix.getElementAt(0, 1));
		assertEquals("!!",matrix.getElementAt(1, 1));
		assertNull(matrix.getElementAt(1, 0));
		
		assertNull(matrix.getElementAt(1000, 0));
		
	}
	

	
	
	//isEmpty at position
	//empty values ae not allowed with set
	//remove value at
	//isInBounds
	
	//insert a column before a position
	//insert a column after
	//inster a column at the start
	//insert a column at the end
	
	//insert a row before a position
	//insert a row after
	//inster a row at the start
	//insert a row at the end

	
	

	
}
