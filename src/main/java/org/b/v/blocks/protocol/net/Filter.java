package org.b.v.blocks.protocol.net;

public interface Filter {
	boolean applies(Message message);
}
