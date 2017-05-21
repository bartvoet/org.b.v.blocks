package org.b.v.blocks.protocol.test.remote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.b.v.blocks.protocol.net.Message;
import org.b.v.blocks.protocol.net.local.LocalNode;
import org.b.v.blocks.protocol.test.FakeBlockRepo;

public class LocalNodeReceiver {
	
	private int port;
	private FakeBlockRepo repo;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public LocalNodeReceiver(FakeBlockRepo repo, int i) {
		this.repo = repo;
		this.port = i;
	}
	
	private static String getContentAsString(DatagramPacket packet) {
		return new String(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
	}

	public void run() {
		try {
			
			final DatagramSocket socket = new DatagramSocket(port);
			executor.submit(new Callable<Void>() {
				public Void call() throws Exception {
					try {
						while(!Thread.currentThread().isInterrupted()) {
								byte[] buf = new byte[256];
								final DatagramPacket packet = new DatagramPacket(buf, buf.length);
								socket.receive(packet);
								String[] content = getContentAsString(packet).split(";");
								if(content[0].equals("broadcast")) {
									for(LocalNode node:repo.allBlocks()) {
										node.receiveMessage(new Message(packet.getAddress().toString(),content[1]));
									}
								} else {
									repo.block(content[0]).receiveMessage(new Message(packet.getAddress().toString(),content[1]));
								}
						}
					}
					catch(Throwable t) {
						socket.close();
						System.out.println("ai");
					}
					return null;
				}
			});
		
		}catch(Throwable t) {
			t.printStackTrace();
		}
	}

}
