package com.iprogrammerr.gentle.request.binary;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class StartEndBinaryParts implements BinaryParts {

    private final byte[] start;
    private final byte[] end;
    private final byte[] source;

    public StartEndBinaryParts(byte[] start, byte[] end, byte[] source) {
	this.start = start;
	this.end = end;
	this.source = source;
    }

    @Override
    public List<byte[]> parts() {
	List<byte[]> parts = new ArrayList<>();
	if (this.source.length > (start.length + end.length)) {
	    int startIndex = 0;
	    for (int i = 0; i < this.source.length; i++) {
		if (startIndex == this.start.length) {
		    byte[] part = part(i);
		    parts.add(part);
		    startIndex = 0;
		    i += part.length - 1;
		} else if (this.source[i] == this.start[startIndex]) {
		    ++startIndex;
		} else {
		    startIndex = 0;
		}
	    }
	}
	return parts;
    }

    private byte[] part(int start) {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for (int i = start; i < this.source.length; i++) {
	    boolean endFound = true;
	    for (int j = 0; j < this.end.length && (i + j) < this.source.length; j++) {
		if (this.source[i + j] != this.end[j]) {
		    endFound = false;
		    break;
		}
	    }
	    if (endFound) {
		break;
	    }
	    baos.write(this.source[i]);
	}
	byte[] part = baos.toByteArray();
	return part;
    }

}
