package com.iprogrammerr.gentle.request.binary;

import java.util.Map;

public final class DefaultBinaryWithAttributes implements BinaryWithAttributes {

	private final Binary origin;
	private final Map<String, String> attributes;

	public DefaultBinaryWithAttributes(Binary origin, Map<String, String> attributes) {
		this.origin = origin;
		this.attributes = attributes;
	}

	public DefaultBinaryWithAttributes(byte[] content, Map<String, String> attributes) {
		this(() -> content, attributes);
	}

	@Override
	public byte[] content() throws Exception {
		return this.origin.content();
	}

	@Override
	public String attribute(String key) throws Exception {
		if (!this.attributes.containsKey(key)) {
			throw new Exception(String.format("There is no attribute associated with %s key", key));
		}
		return this.attributes.get(key);
	}

	@Override
	public boolean has(String key) {
		return this.attributes.containsKey(key);
	}
}
