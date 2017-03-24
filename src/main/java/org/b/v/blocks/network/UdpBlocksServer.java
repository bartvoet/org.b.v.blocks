package org.b.v.blocks.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import org.b.v.blocks.core.Blocks;

public class UdpBlocksServer implements Runnable {

	private int port;
	private DatagramSocket socket;
	private Blocks matrix;

	UdpBlocksServer(int port,Blocks matrix) {
		this.port = port;
		this.matrix = matrix;
	}
	
	@Override
	public void run() {
		try {
			byte[] buf = new byte[256];
			socket = new DatagramSocket(port);
			while(true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				System.out.println("lenght: " + packet.getLength());
				//parseMessage(this.matrix,new String(Arrays.copyOfRange(packet.getData(), 0, packet.getLength())));
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
}