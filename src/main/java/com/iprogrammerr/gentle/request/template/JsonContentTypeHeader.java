package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class JsonContentTypeHeader extends HeaderEnvelope {

	public JsonContentTypeHeader() {
		super(new HttpHeader("Content-Type", "application/json"));
	}
}
