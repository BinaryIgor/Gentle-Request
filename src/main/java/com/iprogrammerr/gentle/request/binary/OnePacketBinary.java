package com.iprogrammerr.gentle.request.binary;

import java.io.InputStream;

public final class OnePacketBinary implements Binary {

    private final InputStream source;
    private final int defaultPacketSize;

    public OnePacketBinary(InputStream source, int defaultPacketSize) {
	this.source = source;
	this.defaultPacketSize = defaultPacketSize;
    }

    public OnePacketBinary(InputStream source) {
	this(source, 512);
    }

    @Override
    public byte[] content() throws Exception {
	int available = this.source.available();
	if (available == 0) {
	    available = this.defaultPacketSize;
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
