package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;

public final class HttpMultipart implements Multipart {

    private static final String TWO_HYPHENS = "--";
    private byte[] parsed;
    private final String type;
    private final String boundary;
    private final List<Part> parts;

    public HttpMultipart(String type, String boundary, byte[] parsed) {
	this.type = type;
	this.parsed = parsed;
	this.boundary = boundary;
	this.parts = new ArrayList<>();
    }

    public HttpMultipart(String type, String boundary, List<Part> parts) {
	this.type = type;
	this.parsed = new byte[0];
	this.boundary = boundary;
	this.parts = parts;
    }

    @Override
    public Iterable<Part> parts() throws Exception {
	if (this.parts.isEmpty()) {
	    List<byte[]> parts = new HttpBoundaryBinaryParts(TWO_HYPHENS + this.boundary).parts(this.parsed);
	    for (byte[] part : parts) {
		this.parts.add(new HttpPart(part));
	    }
	}
	return this.parts;
    }

    @Override
    public String typeHeader() {
	return "multipart/" + this.type + "; boundary=" + this.boundary;
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
