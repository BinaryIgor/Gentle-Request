package com.iprogrammerr.gentle.request.template;

import com.iprogrammerr.gentle.request.Header;

public abstract class HeaderEnvelope implements Header {

	private final Header base;

	public HeaderEnvelope(Header base) {
		this.base = base;
	}

	@Override
	public final String key() {
		return this.base.key();
	}

	@Override
	public final String value() {
		return this.base.value();
	}

}
