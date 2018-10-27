package com.iprogrammerr.gentle.request.template;

import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.Request;

public abstract class RequestEnvelope implements Request {

	private final Request base;

	public RequestEnvelope(Request base) {
		this.base = base;
	}

	@Override
	public final String url() {
		return this.base.url();
	}

	@Override
	public final String method() {
		return this.base.method();
	}

	@Override
	public final void addHeader(Header header) {
		this.base.addHeader(header);
	}

	@Override
	public final List<Header> headers() {
		return this.base.headers();
	}

	@Override
	public final byte[] body() throws Exception {
		return this.base.body();
	}
}
