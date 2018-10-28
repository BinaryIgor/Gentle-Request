package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;
import java.util.List;

public class HttpBoundaryBinaryParts implements BinaryParts {

	private static final String CRLF = "\r\n";
	private final String boundary;
	private final BinaryParts base;
	private final byte[] end;

	public HttpBoundaryBinaryParts(String boundary) {
		this.boundary = boundary;
		this.end = (CRLF + this.boundary + CRLF).getBytes();
		this.base = new StartEndBinaryParts((this.boundary + CRLF).getBytes(), this.end);
	}

	@Override
	public List<byte[]> parts(byte[] source) {
		List<byte[]> parts = this.base.parts(source);
		if (!parts.isEmpty()) {
			byte[] last = parts.get(parts.size() - 1);
			parts.set(parts.size() - 1, Arrays.copyOf(last, last.length - this.end.length));
		}
		return parts;
	}
}
