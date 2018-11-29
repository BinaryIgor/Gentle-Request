package com.iprogrammerr.gentle.request.binary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class TillEndBinary implements Binary {

	private final Binary base;

	public TillEndBinary(InputStream source) {
		this(new OnePacketBinary(source));
	}

	public TillEndBinary(Binary base) {
		this.base = base;
	}

	@Override
	public byte[] content() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			byte[] packet = this.base.content();
			if (packet.length == 0) {
				break;
			}
			baos.write(packet);
		}
		return baos.toByteArray();
	}
}
