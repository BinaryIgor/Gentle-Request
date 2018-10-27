package com.iprogrammerr.gentle.request.mock;

import com.iprogrammerr.bright.server.binary.Binary;

public final class MockedBinary implements Binary {

	private final int min;

	public MockedBinary(int min) {
		this.min = min;
	}

	public MockedBinary() {
		this(1024);
	}

	@Override
	public byte[] content() throws Exception {
		byte[] bytes = new byte[this.min + (int) (Math.random() * 102_400)];
		for (int i = 0; i < bytes.length; ++i) {
			bytes[i] = (byte) (Byte.MIN_VALUE + (Math.random() * 2 * Byte.MAX_VALUE));
		}
		return bytes;
	}

}
