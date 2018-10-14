package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;

public final class HttpMultipartForm implements MultipartForm {

    private static final String TWO_HYPHENS = "--";
    private byte[] parsed;
    private final String boundary;
    private final List<FormPart> parts;

    public HttpMultipartForm(byte[] source, String boundary) {
	this.parsed = source;
	this.boundary = boundary;
	this.parts = new ArrayList<>();
    }

    public HttpMultipartForm(String boundary, List<FormPart> parts) {
	this.parsed = new byte[0];
	this.boundary = boundary;
	this.parts = parts;
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
    public String typeHeader() {
	return "multipart/form-data; boundary=" + this.boundary;
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
