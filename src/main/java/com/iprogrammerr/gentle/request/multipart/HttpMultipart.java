package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

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
	    String boundary = TWO_HYPHENS + this.boundary;
	    String[] parts = new String(this.parsed).split(boundary);
	    for (int i = 1; i < parts.length; i++) {
		this.parts.add(new HttpPart(parts[i].getBytes()));
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
