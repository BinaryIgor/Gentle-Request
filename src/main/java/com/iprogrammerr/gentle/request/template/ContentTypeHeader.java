package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class ContentTypeHeader extends HeaderEnvelope {

	private static final String KEY = "Content-Type";

	public ContentTypeHeader(String type) {
		super(new HttpHeader(KEY, type));
	}

	public ContentTypeHeader(String type, String additional) {
		super(new HttpHeader(KEY, String.format("%s; %s", type, additional)));
	}
}
