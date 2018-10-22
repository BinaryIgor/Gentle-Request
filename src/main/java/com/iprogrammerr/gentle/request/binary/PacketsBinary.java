package com.iprogrammerr.gentle.request.binary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class PacketsBinary implements Binary {

    private final Binary base;
    private final long toRead;

    public PacketsBinary(InputStream source, long toRead) {
	this(new OnePacketBinary(source), toRead);
    }

    public PacketsBinary(Binary base, long toRead) {
	this.base = base;
	this.toRead = toRead;
    }

    @Override
    public byte[] content() throws Exception {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	long read = 0;
	while (read != this.toRead) {
	    byte[] packet = this.base.content();
	    baos.write(packet);
	    read += packet.length;
	}
	return baos.toByteArray();
    }

}
