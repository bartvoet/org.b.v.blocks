package org.b.v.blocks;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class TcpServerTester {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket("localhost",8080);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.println("l;1;2;EAST");
		writer.flush();
		TimeUnit.SECONDS.sleep(1);
		writer.println("l;2;3;SOUTH");
		writer.flush();
		TimeUnit.SECONDS.sleep(1);
		writer.println("l;4;3;NORTH");
		writer.flush();
		TimeUnit.SECONDS.sleep(1);
		writer.println("l;5;4;WEST");
		writer.flush();
		TimeUnit.SECONDS.sleep(1);
		writer.println("l;1;6;WEST");
		
//		matrix.addBlock(1, Orientation.EAST,2);
//		matrix.addBlock(2, Orientation.SOUTH,3);
//		matrix.addBlock(4, Orientation.NORTH,3);
//		matrix.addBlock(5, Orientation.WEST,4);
//		matrix.addBlock(1, Orientation.WEST,6);
		
		writer.close();
	}
}
