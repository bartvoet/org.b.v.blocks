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
		writer.close();
		socket.close();
	}
}
