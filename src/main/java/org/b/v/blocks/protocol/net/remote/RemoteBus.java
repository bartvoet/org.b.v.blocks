package org.b.v.blocks.protocol.net.remote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.b.v.blocks.protocol.net.Bus;
import org.b.v.blocks.protocol.net.Filter;
import org.b.v.blocks.protocol.net.Message;

public class RemoteBus implements Bus {

	private static final String BROAD_CAST_ADRESS = "192.168.0.255";

	private static final int DEFAULT_UDP_PORT = 8080;

	private String broadCastAdress;
	private int portRx;
	private int portTx;
	
	
	public interface MessageTransformer {
		public Message beforeBroadcast(Message message);
		public Message beforeSending(Message message);
		public Message whenReceived(Message message);
	}
	
	public static MessageTransformer NO_MESSAGE_TRANSFORMATION_REQUIRED = new MessageTransformer() {
		
		@Override
		public Message whenReceived(Message message) {
			return message;
		}
		
		@Override
		public Message beforeSending(Message message) {
			return message;
		}

		@Override
		public Message beforeBroadcast(Message message) {
			return message;
		}
	};
	
	private MessageTransformer transformer = NO_MESSAGE_TRANSFORMATION_REQUIRED;

	
	public RemoteBus() {
		this(DEFAULT_UDP_PORT,DEFAULT_UDP_PORT,BROAD_CAST_ADRESS);
	}

	public RemoteBus(int port) {
		this(port,port,BROAD_CAST_ADRESS);
	}
	
	public RemoteBus(int portRx,int portTx) {
		this(portRx,portTx,BROAD_CAST_ADRESS);
	}
	
	public RemoteBus(int portRx,int portTx,String broadCastAdress) {
		this.portRx = portRx;
		this.portTx = portTx;
		this.broadCastAdress = broadCastAdress;
		try {
			socket = new DatagramSocket(portRx);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public RemoteBus withTransformer(MessageTransformer transformer) {
		this.transformer = transformer;
		return this;
	}
	
	@Override
	public void broadcast(String content) {
		System.out.println("broadcast" + " =>" + content);
		Message message = transformer.beforeBroadcast(new Message(this.broadCastAdress,content));
		this.sendBytes(message.getIp(), message.getKey());
	}

	@Override
	public void sendMessage(String ip, String content) {
		System.out.println(ip + " =>" + content);
		Message message = transformer.beforeSending(new Message(ip,content));
		sendBytes(message.getIp(),message.getKey());
	}
	
	public void sendBytes(String ip, String content) {
		try {

			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = 
					new DatagramPacket(
							content.getBytes(), content.getBytes().length,
							InetAddress.getByName(ip), portTx);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			System.out.println("ambetant");
			throw new RuntimeException(e);
		}
	}

	private static ExecutorService executor = Executors.newScheduledThreadPool(10);
	
	private static String getContentAsString(DatagramPacket packet) {
		return new String(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
	}
	
	private static void interruptFutureAfter(Future<Void> future,int time,TimeUnit unit) throws InterruptedException {
		unit.sleep(time);
		if(!future.isDone()) {
			future.cancel(true);
		}
	}
	
	private DatagramSocket socket = null;
	
	@Override
	public List<Message> waitForMessages(int number, TimeUnit unit, final Filter... filters) {
		final List<Message> messages = new ArrayList<Message>();
		try {
			socket.setSoTimeout(number);
			byte[] buf = new byte[256];
			final DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			Future<Void> future = executor.submit(new Callable<Void>() {
				public Void call() throws Exception {
					try {
						while(!Thread.currentThread().isInterrupted()) {
							try {
								socket.receive(packet);
								String content = getContentAsString(packet);
								Message message = 
										transformer.whenReceived(
													new Message(
															packet.getAddress().getHostAddress(),
															content)
												);
								System.out.println(message.getKey() + " <= " + message.getIp());
								boolean applies = true;
								for(Filter filter : filters) {
									if(!filter.applies(message)) {
										applies=false;
									}
								}
								if(applies) {
									messages.add(message);
								}
								
							}
							catch(SocketTimeoutException e) {
								//can be ignored, will be checked in while-condition
							}
						}
					}
					catch(Throwable t) {
						//socket.close();
					}
					finally {
						//System.out.println("close");
						//socket.close();
					}
					return null;
				}
				
			});
		
		interruptFutureAfter(future,number,unit);
		if(!future.isDone()) {
			future.get();
		}
		//TODO close
		}catch(Throwable t) {
			t.printStackTrace();
		}
		return messages;
	}

	@Override
	public Message waitForSingleMessage(int number, TimeUnit unit, Filter startsWith) {
		List<Message> messages = this.waitForMessages(number, unit, startsWith);
		if(messages.isEmpty()) {
			return null;
		}
		return messages.get(0);
	}
}
