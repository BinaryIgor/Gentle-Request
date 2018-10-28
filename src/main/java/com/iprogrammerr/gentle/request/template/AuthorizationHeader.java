package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.HttpHeader;

public final class AuthorizationHeader extends HeaderEnvelope {

	public AuthorizationHeader(String value) {
		super(new HttpHeader("Authorization", value));
	}
}
