package org.b.v.blocks.protocol.test;
import org.b.v.blocks.core.Matrix;
import org.b.v.blocks.protocol.Protocol;
import org.b.v.blocks.protocol.net.local.LocalBus;

public class ProtocolTest {

	private LocalBus bus = new LocalBus("100.2.2.2");
	private FakeBlockRepo repo =  new FakeBlockRepo(bus);

	private FakeBlock block(String ip) {
		return repo.block(ip);
	}
	
	public ProtocolTest() {
		bus.addNode(block("100.0.0.0"));
		bus.addNode(block("100.0.0.1"));
		bus.addNode(block("100.0.0.2"));
		
		block("100.0.0.1").connectNorthOf("100.0.0.0");
		block("100.0.0.2").connectEastOf("100.0.0.0");
		block("100.0.0.2").rotate();
	}
	
	public void run() {
		Matrix<String> matrix = new Protocol(bus).run();
		
		System.out.println();
		System.out.println("result:");
		System.out.println(matrix);
	}
	
	public static void main(String[] args) {
		new ProtocolTest().run();
	}
}
