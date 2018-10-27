package com.iprogrammerr.gentle.request.data;

import java.util.List;

public interface TypedMap {

	TypedMap put(String key, Object value);

	<T> boolean has(String key, Class<T> clazz);

	boolean booleanValue(String key) throws Exception;

	int intValue(String key) throws Exception;

	long longValue(String key) throws Exception;

	String stringValue(String key) throws Exception;

	float floatValue(String key) throws Exception;

	double doubleValue(String key) throws Exception;

	byte[] binaryValue(String key) throws Exception;

	List<? extends KeyValue> keyValues();

	boolean isEmpty();

	int size();

}
