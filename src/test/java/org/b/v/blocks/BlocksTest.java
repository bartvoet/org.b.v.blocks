package org.b.v.blocks;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlocksTest {

	@Test
	public void test() {
		Blocks matrix = new Blocks();
		matrix.addBlockRelationShip(1, Orientation.EAST,2);
		matrix.addBlockRelationShip(2, Orientation.SOUTH,3);
		matrix.addBlockRelationShip(4, Orientation.NORTH,3);
		matrix.addBlockRelationShip(5, Orientation.WEST,4);
		matrix.addBlockRelationShip(1, Orientation.WEST,6);
	}

}
