package com.iprogrammerr.gentle.request;

import java.util.ArrayList;
import java.util.List;

public final class Response {

    private final int code;
    private final List<Header> headers;
    private final byte[] body;

    public Response(int code, List<Header> headers, byte[] body) {
	this.code = code;
	this.headers = headers;
	this.body = body;
    }

    public Response(int code, byte[] body) {
	this(code, new ArrayList<>(), body);
    }

    public Response(int code, List<Header> headers) {
	this(code, headers, new byte[0]);
    }

    public Response(int code) {
	this(code, new byte[0]);
    }

    public int code() {
	return this.code;
    }

    public boolean hasProperCode() {
	return this.code >= 200 && this.code < 300;
    }

    public List<Header> headers() {
	return this.headers;
    }

    public byte[] body() {
	return this.body;
    }
}
