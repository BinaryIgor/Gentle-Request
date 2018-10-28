package com.iprogrammerr.gentle.request.binary;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class StartEndBinaryParts implements BinaryParts {

	private final byte[] start;
	private final byte[] end;

	public StartEndBinaryParts(byte[] start, byte[] end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public List<byte[]> parts(byte[] source) {
		List<byte[]> parts = new ArrayList<>();
		if (source.length > this.start.length) {
			int startIndex = 0;
			for (int i = 0; i < source.length; i++) {
				if (startIndex == this.start.length) {
					byte[] part = part(i, source);
					parts.add(part);
					startIndex = 0;
					i += part.length - 1;
				} else if (source[i] == this.start[startIndex]) {
					++startIndex;
				} else if ((source.length - i) < this.start.length) {
					break;
				} else {
					startIndex = 0;
				}
			}
		}
		return parts;
	}

	private byte[] part(int start, byte[] source) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(source.length / 4);
		for (int i = start; i < source.length; i++) {
			boolean endFound = true;
			for (int j = 0; j < this.end.length && (i + j) < source.length; j++) {
				if (source[i + j] != this.end[j]) {
					endFound = false;
					break;
				}
			}
			if (endFound) {
				break;
			}
			baos.write(source[i]);
		}
		byte[] part = baos.toByteArray();
		return part;
	}
}
