package org.b.v.blocks.protocol.test.remote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.b.v.blocks.protocol.net.MessageBox;

public class RemoteMessageBox implements MessageBox {

	private String ipTo;
	private int portTx;

	public RemoteMessageBox(String ipTo, int portTx) {
		this.ipTo = ipTo;
		this.portTx = portTx;
	}

	@Override
	public void postMessage(String ipFrom, String content) {
		try {
			String wrappedMessage = ipFrom + ";" + content;
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(
					wrappedMessage.getBytes(), wrappedMessage.getBytes().length,
					InetAddress.getByName(ipTo), portTx);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
