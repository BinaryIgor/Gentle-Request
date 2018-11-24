package com.iprogrammerr.gentle.request.data;

import java.util.List;

public interface Primitives {

	Primitives put(String key, Object value);

	<T> boolean has(String key, Class<T> clazz);

	boolean booleanValue(String key) throws Exception;

	Number numberValue(String key) throws Exception;

	String stringValue(String key) throws Exception;

	byte[] binaryValue(String key) throws Exception;

	List<KeyValue> keyValues();

	boolean isEmpty();
}
