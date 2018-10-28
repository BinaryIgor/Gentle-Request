package com.iprogrammerr.gentle.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EmptyRequest implements Request {

	private final String method;
	private final String url;
	private final List<Header> headers;
	private final byte[] body;

	private EmptyRequest(String method, String url, List<Header> headers, byte[] body) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.body = body;
	}

	public EmptyRequest(String method, String url, List<Header> headers) {
		this(method, url, headers, new byte[0]);
	}

	public EmptyRequest(String method, String url, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), new byte[0]);
	}

	@Override
	public String method() {
		return this.method;
	}

	@Override
	public String url() {
		return this.url;
	}

	@Override
	public List<Header> headers() {
		return this.headers;
	}

	@Override
	public void addHeader(Header header) {
		this.headers.add(header);
	}

	@Override
	public byte[] body() throws Exception {
		return this.body;
	}
}
