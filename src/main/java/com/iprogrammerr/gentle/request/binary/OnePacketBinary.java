package com.iprogrammerr.gentle.request.binary;

import java.io.InputStream;

public final class OnePacketBinary implements Binary {

	private final InputStream source;
	private final int defaultSize;

	public OnePacketBinary(InputStream source, int defaultSize) {
		this.source = source;
		this.defaultSize = defaultSize;
	}

	public OnePacketBinary(InputStream source) {
		this(source, 1024);
	}

	@Override
	public byte[] content() throws Exception {
		int available = this.source.available();
		if (available == 0) {
			available = this.defaultSize;
		}
		byte[] buffer = new byte[available];
		int read = this.source.read(buffer);
		byte[] readBytes;
		if (read <= 0) {
			readBytes = new byte[0];
		} else if (read == buffer.length) {
			readBytes = buffer;
		} else {
			readBytes = new byte[read];
			for (int i = 0; i < read; i++) {
				readBytes[i] = buffer[i];
			}
		}
		return readBytes;
	}
}
