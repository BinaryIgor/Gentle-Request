package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class ContentLengthHeader extends HeaderEnvelope {

	public ContentLengthHeader(int value) {
		super(new HttpHeader("Content-Length", String.valueOf(value)));
	}
}
