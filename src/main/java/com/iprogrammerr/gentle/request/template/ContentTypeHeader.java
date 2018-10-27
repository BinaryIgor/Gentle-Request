package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class ContentTypeHeader extends HeaderEnvelope {

	public ContentTypeHeader(String type) {
		super(new HttpHeader("Content-Type", type));
	}
}
