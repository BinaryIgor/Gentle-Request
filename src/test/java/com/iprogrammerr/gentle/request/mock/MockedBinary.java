package com.iprogrammerr.gentle.request.mock;

import com.iprogrammerr.bright.server.binary.Binary;

public final class MockedBinary implements Binary {

	private final int min;
	private final int max;

	public MockedBinary(int min, int max) {
		if (min > max) {
			throw new RuntimeException(String.format("min %d is greater than %d max", min, max));
		}
		this.min = min;
		this.max = max;
	}

	public MockedBinary() {
		this(1024, 102_400);
	}

	@Override
	public byte[] content() throws Exception {
		byte[] bytes = new byte[this.min + (int) (Math.random() * (this.max - this.min))];
		for (int i = 0; i < bytes.length; ++i) {
			bytes[i] = (byte) (Byte.MIN_VALUE + (Math.random() * 2 * Byte.MAX_VALUE));
		}
		return bytes;
	}
}
