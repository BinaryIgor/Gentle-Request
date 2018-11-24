package com.iprogrammerr.gentle.request.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Attributes implements Primitives {

	private final List<KeyValue> values;

	private Attributes(List<KeyValue> values) {
		this.values = values;
	}

	public Attributes() {
		this(new ArrayList<>(10));
	}

	public Attributes(int initialSize) {
		this(new ArrayList<>(initialSize));
	}

	@Override
	public Primitives put(String key, Object value) {
		int index = index(key);
		if (index >= 0) {
			this.values.set(index, new StringObject(key, value));
		} else {
			this.values.add(new StringObject(key, value));
		}
		return this;
	}

	private int index(String key) {
		int index = -1;
		for (int i = 0; i < this.values.size(); i++) {
			if (this.values.get(i).key().equals(key)) {
				index = i;
				break;
			}
		}
		return index;
	}

	@Override
	public Number numberValue(String key) throws Exception {
		return value(key, Number.class);
	}

	@Override
	public boolean booleanValue(String key) throws Exception {
		return value(key, Boolean.class);
	}

	@Override
	public String stringValue(String key) throws Exception {
		return value(key, String.class);
	}

	@Override
	public byte[] binaryValue(String key) throws Exception {
		return value(key, byte[].class);
	}

	@Override
	public List<KeyValue> keyValues() {
		return new ArrayList<>(this.values);
	}

	private <T> T value(String key, Class<T> clazz) throws Exception {
		for (KeyValue kv : this.values) {
			if (kv.key().equals(key) && clazz.isAssignableFrom(kv.value().getClass())) {
				return clazz.cast(kv.value());
			}
		}
		throw new Exception(String.format("Key %s of type %s is not present", key, clazz));
	}

	@Override
	public boolean isEmpty() {
		return this.values.isEmpty();
	}

	@Override
	public <T> boolean has(String key, Class<T> clazz) {
		int index = index(key);
		if (index >= 0) {
			return clazz.isAssignableFrom(this.values.get(index).value().getClass());
		}
		return false;
	}

	@Override
	public boolean equals(Object object) {
		boolean equal;
		if (!Primitives.class.isAssignableFrom(object.getClass())) {
			equal = false;
		} else if (object == this) {
			equal = true;
		} else {
			Primitives other = (Primitives) object;
			equal = true;
			for (KeyValue kv : this.values) {
				equal = hasEqualEntry(other, kv);
				if (!equal) {
					break;
				}
			}
		}
		return equal;
	}

	private boolean hasEqualEntry(Primitives primitives, KeyValue keyValue) {
		boolean equal;
		try {
			if (primitives.has(keyValue.key(), Boolean.class)) {
				equal = this.booleanValue(keyValue.key()) == primitives.booleanValue(keyValue.key());
			} else if (primitives.has(keyValue.key(), Number.class)) {
				equal = Math.abs(this.numberValue(keyValue.key()).doubleValue()
						- primitives.numberValue(keyValue.key()).doubleValue()) < 10e-6;
			} else if (primitives.has(keyValue.key(), String.class)) {
				equal = this.stringValue(keyValue.key()).equals(primitives.stringValue(keyValue.key()));
			} else if (primitives.has(keyValue.key(), byte[].class)) {
				equal = Arrays.equals(this.binaryValue(keyValue.key()), primitives.binaryValue(keyValue.key()));
			} else {
				equal = false;
			}
		} catch (Exception e) {
			equal = false;
		}
		return equal;
	}

	@Override
	public String toString() {
		return "Attributes [values=" + values + "]";
	}
}
