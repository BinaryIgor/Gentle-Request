package com.iprogrammerr.gentle.request.data;

import java.util.List;

public interface KeysValues {

    <T> boolean has(String key, Class<T> clazz);

    <T> T value(String key, Class<T> clazz) throws Exception;

    KeysValues put(String key, Object value);

    List<KeyValue> keysValues();

    boolean isEmpty();
}
