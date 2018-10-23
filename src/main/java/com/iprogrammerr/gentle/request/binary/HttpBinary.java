package com.iprogrammerr.gentle.request.binary;

import org.json.JSONObject;

public final class HttpBinary implements SmartBinary {

    private final byte[] source;

    public HttpBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] value() {
	return this.source;
    }

    @Override
    public String stringValue() {
	return new String(this.source);
    }

    @Override
    public JSONObject jsonValue() throws Exception {
	return this.source.length > 0 ? new JSONObject(new String(this.source)) : new JSONObject();
    }

}
