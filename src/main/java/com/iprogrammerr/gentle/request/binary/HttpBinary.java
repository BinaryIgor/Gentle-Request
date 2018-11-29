package com.iprogrammerr.gentle.request.binary;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public final class HttpBinary implements SmartBinary {

	private final byte[] source;
	private final Charset charset;

	public HttpBinary(byte[] source, Charset charset) {
		this.source = source;
		this.charset = charset;
	}

	public HttpBinary(byte[] source) {
		this(source, StandardCharsets.UTF_8);
	}

	@Override
	public byte[] value() {
		return this.source;
	}

	@Override
	public String stringValue() {
		return new String(this.source, this.charset);
	}

	@Override
	public JSONObject jsonValue() throws Exception {
		return this.source.length > 0 ? new JSONObject(new String(this.source, this.charset)) : new JSONObject();
	}
}
