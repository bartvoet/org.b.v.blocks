package org.b.v.blocks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class UdpServerTester {
	private static DatagramSocket socket;
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		socket = new DatagramSocket();
		send("l;1;2;EAST");
		TimeUnit.SECONDS.sleep(1);
		send("l;2;3;SOUTH");
		TimeUnit.SECONDS.sleep(1);
		send("l;4;3;NORTH");
		TimeUnit.SECONDS.sleep(1);
		send("l;5;4;WEST");
		TimeUnit.SECONDS.sleep(1);
		send("l;1;6;WEST");
	}
	
	private static void send(String message) throws IOException {
       DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName("localhost"), 8081);
       socket.send(packet);
	}
}
