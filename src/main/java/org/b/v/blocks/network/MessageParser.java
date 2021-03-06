package org.b.v.blocks.network;

import javax.swing.JPanel;

import org.b.v.blocks.core.Blocks;
import org.b.v.blocks.core.Orientation;

public class MessageParser {
	public void parseMessage(Blocks matrix, String line) {
		String[] tokens = line.split(";");
		for (String token : tokens) {
			System.out.println(token);
		}
		String id = (tokens[1]);
		String other = (tokens[2]);
		Orientation orientation = Orientation.valueOf(tokens[3]);
		matrix.addBlockRelationShip(id, orientation, other);

//		frame.setContentPane(new JPanel());
//		frame.getContentPane().setLayout(null);
//
//		matrix.drawBlocks(frame);
	}
}
