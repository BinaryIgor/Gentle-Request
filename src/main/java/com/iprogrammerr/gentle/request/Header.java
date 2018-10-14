package com.iprogrammerr.gentle.request;

public final class Header {

    private final String key;
    private final String value;

    public Header(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public String key() {
	return this.key;
    }

    public String value() {
	return this.value;
    }

    public boolean is(String key) {
	return this.key.equalsIgnoreCase(key);
    }
}
