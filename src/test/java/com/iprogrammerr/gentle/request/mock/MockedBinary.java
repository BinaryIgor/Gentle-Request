package com.iprogrammerr.gentle.request.mock;

import java.io.BufferedInputStream;
import java.io.InputStream;

public final class MockedBinary {

    private final InputStream source;

    public MockedBinary(InputStream source) {
	this.source = source;
    }

    public MockedBinary() {
	this(MockedBinary.class.getResourceAsStream("/java.png"));
    }

    public byte[] content() throws Exception {
	try (BufferedInputStream is = new BufferedInputStream(source)) {
	    int available = is.available();
	    byte[] content = new byte[available];
	    is.read(content);
	    return content;
	}
    }
}
