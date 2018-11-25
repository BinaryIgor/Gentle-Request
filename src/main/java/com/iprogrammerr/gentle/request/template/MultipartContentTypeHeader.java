package com.iprogrammerr.gentle.request.template;

public final class MultipartContentTypeHeader extends HeaderEnvelope {

	public MultipartContentTypeHeader(String type, String boundary) {
		super(new ContentTypeHeader("multipart/" + type, "boundary=" + boundary));
	}

	public MultipartContentTypeHeader(String boundary) {
		this("form-data", boundary);
	}
}
