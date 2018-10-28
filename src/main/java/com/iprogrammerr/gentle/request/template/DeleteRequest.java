package com.iprogrammerr.gentle.request.template;

import java.util.List;

import com.iprogrammerr.gentle.request.EmptyRequest;
import com.iprogrammerr.gentle.request.Header;

public final class DeleteRequest extends RequestEnvelope {

	private static final String DELETE = "DELETE";

	public DeleteRequest(String url, List<Header> headers) {
		super(new EmptyRequest(DELETE, url, headers));
	}

	public DeleteRequest(String url, Header... headers) {
		super(new EmptyRequest(DELETE, url, headers));
	}
}
