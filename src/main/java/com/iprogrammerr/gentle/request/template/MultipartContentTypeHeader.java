package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class MultipartContentTypeHeader extends HeaderEnvelope {

	public MultipartContentTypeHeader(String type, String boundary) {
		super(new HttpHeader("Content-Type", "multipart/" + type + "; boundary=" + boundary));
	}

	public MultipartContentTypeHeader(String boundary) {
		super(new HttpHeader("Content-Type", "multipart/form-data; boundary=" + boundary));
	}
}
