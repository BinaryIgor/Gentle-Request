package com.iprogrammerr.gentle.request.data;

import java.util.ArrayList;
import java.util.List;

public final class StringsObjects implements KeysValues {

    private final List<KeyValue> keysValues;

    public StringsObjects() {
	this.keysValues = new ArrayList<>();
    }

    public StringsObjects(List<KeyValue> keyValues) {
	this.keysValues = keyValues;
    }

    @Override
    public <T> boolean has(String key, Class<T> clazz) {
	for (KeyValue kv : this.keysValues) {
	    if (kv.key().equals(key) && kv.value().getClass().isAssignableFrom(clazz)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public <T> T value(String key, Class<T> clazz) throws Exception {
	for (KeyValue kv : this.keysValues) {
	    if (kv.key().equals(key) && kv.value().getClass().isAssignableFrom(clazz)) {
		return clazz.cast(kv.value());
	    }
	}
	throw new Exception(String.format("%s associated with %s key is not present", clazz, key));
    }

    @Override
    public KeysValues put(String key, Object value) {
	KeyValue keyValue = new StringObject(key, value);
	int previous = index(keyValue.key());
	if (previous >= 0) {
	    this.keysValues.set(previous, keyValue);
	} else {
	    this.keysValues.add(keyValue);
	}
	return this;
    }

    private int index(String key) {
	for (int i = 0; i < this.keysValues.size(); i++) {
	    if (this.keysValues.get(i).key().equals(key)) {
		return i;
	    }
	}
	return -1;
    }

    @Override
    public boolean isEmpty() {
	return this.keysValues.isEmpty();
    }

    @Override
    public List<KeyValue> keysValues() {
	return this.keysValues;
    }

}
