package com.iprogrammerr.gentle.request.binary;

public final class BinaryWithSingleAttribute implements BinaryWithAttributes {

	private final Binary origin;
	private final String key;
	private final String value;

	public BinaryWithSingleAttribute(Binary origin, String key, String value) {
		this.origin = origin;
		this.key = key;
		this.value = value;
	}

	public BinaryWithSingleAttribute(byte[] content, String key, String value) {
		this(() -> content, key, value);
	}

	@Override
	public byte[] content() throws Exception {
		return this.origin.content();
	}

	@Override
	public String attribute(String key) throws Exception {
		if (!this.key.equals(key)) {
			throw new Exception(String.format("There is no attribute associated with %s key", key));
		}
		return this.value;
	}

	@Override
	public boolean has(String key) {
		return this.key.equals(key);
	}
}
