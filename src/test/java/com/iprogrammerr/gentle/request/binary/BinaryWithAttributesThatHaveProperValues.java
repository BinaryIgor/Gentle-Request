package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryWithAttributesThatHaveProperValues extends TypeSafeMatcher<BinaryWithAttributes> {

	private final byte[] source;
	private final Map<String, String> attributes;

	public BinaryWithAttributesThatHaveProperValues(byte[] source, Map<String, String> attributes) {
		this.source = source;
		this.attributes = attributes;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryWithAttributes item) {
		boolean matched;
		try {
			matched = Arrays.equals(this.source, item.content());
			if (matched) {
				for (Map.Entry<String, String> a : this.attributes.entrySet()) {
					matched = item.attribute(a.getKey()).equals(a.getValue());
					if (!matched) {
						break;
					}
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
