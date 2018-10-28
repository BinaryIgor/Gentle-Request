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

	@Override
	public final boolean is(String key) {
		return this.base.is(key);
	}

	@Override
	public final boolean equals(Object object) {
		return this.base.equals(object);
	}

	@Override
	public final String toString() {
		return this.base.toString();
	}
}
