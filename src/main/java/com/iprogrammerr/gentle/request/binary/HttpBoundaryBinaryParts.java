package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;
import java.util.List;

//TODO optimization
public class HttpBoundaryBinaryParts implements BinaryParts {

    private static final String CRLF = "\r\n";
    private final String boundary;
    private final BinaryParts binaryParts;
    private final byte[] end;

    public HttpBoundaryBinaryParts(String boundary, byte[] source) {
	this.boundary = boundary;
	this.end = (CRLF + this.boundary + CRLF).getBytes();
	this.binaryParts = new StartEndBinaryParts((this.boundary + CRLF).getBytes(), this.end, source);
    }

    @Override
    public List<byte[]> parts() {
	List<byte[]> parts = this.binaryParts.parts();
	if (!parts.isEmpty()) {
	    byte[] last = parts.get(parts.size() - 1);
	    parts.set(parts.size() - 1, Arrays.copyOf(last, last.length - this.end.length));
	}
	return parts;
    }

}
