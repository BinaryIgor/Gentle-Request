package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryThatContains extends TypeSafeMatcher<Binary> {

	private final byte[] source;

	public BinaryThatContains(byte[] source) {
		this.source = source;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Binary item) {
		boolean matched;
		try {
			matched = Arrays.equals(this.source, item.content());
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
