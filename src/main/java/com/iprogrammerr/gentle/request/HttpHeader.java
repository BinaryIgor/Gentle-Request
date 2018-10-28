package com.iprogrammerr.gentle.request;

public final class HttpHeader implements Header {

	private final String key;
	private final String value;

	public HttpHeader(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String key() {
		return this.key;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public boolean is(String key) {
		return this.key.equalsIgnoreCase(key);
	}

	@Override
	public boolean equals(Object object) {
		boolean equal;
		if (this == object) {
			equal = true;
		} else if (object == null || object.getClass().isAssignableFrom(Header.class)) {
			equal = false;
		} else {
			Header other = (Header) object;
			equal = is(other.key()) && this.value.equals(other.value());
		}
		return equal;
	}

	@Override
	public String toString() {
		return this.key + ": " + this.value;
	}
}
