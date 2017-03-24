package org.b.v.blocks.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.b.v.blocks.core.Blocks;

public class TcpBlocksServer implements Runnable {
	
	private int port;
	private Blocks matrix;
	private MessageParser parser = new MessageParser();

	public TcpBlocksServer(int port,Blocks matrix) {
		this.port = 8080;
		this.matrix=matrix;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(this.port);
			while (true) {
				Socket socket = server.accept();
				socket.getInputStream();
				Scanner in = new Scanner(socket.getInputStream());
				
				while (in.hasNextLine()) {
					String line = in.nextLine();
					parser.parseMessage(this.matrix, line);
				}
				in.close();
				server.close();
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}