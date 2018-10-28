package com.iprogrammerr.gentle.request.template;

import java.util.List;

import com.iprogrammerr.gentle.request.EmptyRequest;
import com.iprogrammerr.gentle.request.Header;

public final class GetRequest extends RequestEnvelope {

	private static final String GET = "GET";

	public GetRequest(String url, List<Header> headers) {
		super(new EmptyRequest(GET, url, headers));
	}

	public GetRequest(String url, Header... headers) {
		super(new EmptyRequest(GET, url, headers));
	}
}
