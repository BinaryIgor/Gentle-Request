package com.iprogrammerr.gentle.request.binary;

public interface BinaryWithAttributes extends Binary {

	String attribute(String key) throws Exception;

	boolean has(String key);
}
