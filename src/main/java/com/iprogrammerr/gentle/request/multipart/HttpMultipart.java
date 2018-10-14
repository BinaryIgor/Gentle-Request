package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;

public final class HttpMultipart implements Multipart {

    private static final String TWO_HYPHENS = "--";
    private final Header header;
    private byte[] parsed;
    private final String boundary;
    private final List<Part> parts;

    private HttpMultipart(Header header, String boundary, byte[] parsed, List<Part> parts) {
	this.header = header;
	this.parsed = parsed;
	this.boundary = boundary;
	this.parts = parts;
    }

    private HttpMultipart(String type, String boundary, byte[] parsed, List<Part> parts) {
	this(new Header("Content-Type", "multipart/" + type + "; boundary=" + boundary), boundary, parsed, parts);
    }

    public HttpMultipart(String type, String boundary, byte[] parsed) {
	this(type, boundary, parsed, new ArrayList<>());
    }

    public HttpMultipart(String type, String boundary, List<Part> parts) {
	this(type, boundary, new byte[0], parts);
    }

    @Override
    public List<Part> parts() {
	if (this.parts.isEmpty()) {
	    List<byte[]> parts = new HttpBoundaryBinaryParts(TWO_HYPHENS + this.boundary).parts(this.parsed);
	    for (byte[] part : parts) {
		this.parts.add(new HttpPart(part));
	    }
	}
	return this.parts;
    }

    @Override
    public Header header() {
	return this.header;
    }

    @Override
    public byte[] body() throws Exception {
	if (this.parsed.length < 1) {
	    this.parsed = new HttpMultipartBody(this.boundary, this.parts.toArray(new Part[this.parts.size()]))
		    .content();
	}
	return this.parsed;
    }

}
