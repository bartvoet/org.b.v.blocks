package org.b.v.blocks.protocol.test.remote;

import org.b.v.blocks.core.Matrix;
import org.b.v.blocks.protocol.Protocol;
import org.b.v.blocks.protocol.net.remote.RemoteBus;

public class RealRemoteProtocolTest {

	public static void main(String[] args) {
		//bus
		Matrix<String> matrix = new Protocol(new RemoteBus(8080)).run();
		System.out.println(matrix);
		System.out.println("finished");
	}

}
