package org.b.v.blocks.protocol.test.remote;

import org.b.v.blocks.protocol.Protocol;
import org.b.v.blocks.protocol.net.remote.IpMessageTransformer;
import org.b.v.blocks.protocol.net.remote.RemoteBus;
import org.b.v.blocks.protocol.test.FakeBlockRepo;

public class RemoteProtocolTest {
	

	public static void main(String[] args) {
		IpMessageTransformer transformer = new IpMessageTransformer();

		//block-simulation
		RemoteMessageBox box = new RemoteMessageBox("localhost",8081);//sends
		FakeBlockRepo repo = new FakeBlockRepo(box);
		repo.block("100.0.0.1").connectNorthOf("100.0.0.0");
		repo.block("100.0.0.2").connectEastOf("100.0.0.0");
		repo.block("100.0.0.2").rotate();
		
		LocalNodeReceiver receiver = new LocalNodeReceiver(repo,8082);//receives
		receiver.run();
		
		//bus
//		RemoteBus bus = new RemoteBus(8081,8082,"localhost").withTransformer(transformer);
//		Protocol protocol = new Protocol(bus);
//		System.out.println(protocol.run());
//		System.out.println("finished");
	}

}
