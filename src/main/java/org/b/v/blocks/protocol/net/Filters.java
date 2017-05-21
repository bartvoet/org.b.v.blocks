package org.b.v.blocks.protocol.net;

public class Filters {
	public static Filter containsKey(final String start) {
		return new Filter() {
			public boolean applies(Message message) {
				return message.getKey().contains(start);
			}
		};
	}
}
