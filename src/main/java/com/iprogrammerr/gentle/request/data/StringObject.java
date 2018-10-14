package com.iprogrammerr.gentle.request.data;

public final class StringObject implements KeyValue {

    private final String key;
    private final Object value;

    public StringObject(String key, Object value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public String key() {
	return this.key;
    }

    @Override
    public Object value() {
	return this.value;
    }

}
