package com.iprogrammerr.gentle.request;

public interface Header {

	String key();

	String value();

	boolean is(String key);
}
