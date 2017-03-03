package org.b.v.blocks;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlocksTest {

	@Test
	public void test() {
		Blocks matrix = new Blocks();
		matrix.addBlock(1, Orientation.EAST,2);
		matrix.addBlock(2, Orientation.SOUTH,3);
		matrix.addBlock(4, Orientation.NORTH,3);
		matrix.addBlock(5, Orientation.WEST,4);
		matrix.addBlock(1, Orientation.WEST,6);
		
		
		
	}

}
