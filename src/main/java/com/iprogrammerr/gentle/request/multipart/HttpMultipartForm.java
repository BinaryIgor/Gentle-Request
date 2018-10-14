package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;

public final class HttpMultipartForm implements MultipartForm {

    private static final String TWO_HYPHENS = "--";
    private final Header header;
    private final String boundary;
    private byte[] parsed;
    private final List<FormPart> parts;

    private HttpMultipartForm(Header header, String boundary, byte[] parsed, List<FormPart> parts) {
	this.header = header;
	this.boundary = boundary;
	this.parsed = parsed;
	this.parts = parts;
    }

    private HttpMultipartForm(String boundary, byte[] parsed, List<FormPart> parts) {
	this(new Header("Content-Type", "multipart/form-data; boundary=" + boundary), boundary, parsed, parts);
    }

    public HttpMultipartForm(String boundary, byte[] parsed) {
	this(boundary, parsed, new ArrayList<>());
    }

    public HttpMultipartForm(String boundary, List<FormPart> parts) {
	this(boundary, new byte[0], parts);
    }

    @Override
    public Iterable<FormPart> parts() throws Exception {
	if (this.parts.isEmpty()) {
	    List<byte[]> parts = new HttpBoundaryBinaryParts(TWO_HYPHENS + this.boundary).parts(this.parsed);
	    for (byte[] part : parts) {
		this.parts.add(new HttpFormPart(part));
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
	    this.parsed = new HttpMultipartBody(this.boundary, this.parts.toArray(new FormPart[this.parts.size()]))
		    .content();
	}
	return this.parsed;
    }

}
