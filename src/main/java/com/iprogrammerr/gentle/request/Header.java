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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + this.key.hashCode();
	result = prime * result + this.value.hashCode();
	return result;
    }

    @Override
    public boolean equals(Object object) {
	boolean equal;
	if (this == object) {
	    equal = true;
	} else if (object == null || getClass() != object.getClass()) {
	    equal = false;
	} else {
	    Header other = (Header) object;
	    equal = other.is(this.key) && this.value.equals(other.value);
	}
	return equal;
    }

}
