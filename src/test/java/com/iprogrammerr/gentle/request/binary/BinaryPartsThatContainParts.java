package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryPartsThatContainParts extends TypeSafeMatcher<BinaryParts> {

	private final byte[] source;
	private final List<byte[]> parts;

	public BinaryPartsThatContainParts(byte[] source, List<byte[]> parts) {
		this.source = source;
		this.parts = parts;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryParts item) {
		boolean matched;
		try {
			List<byte[]> parts = item.parts(this.source);
			if (parts.size() == this.parts.size()) {
				matched = true;
				for (int i = 0; i < parts.size(); ++i) {
					if (!Arrays.equals(parts.get(i), this.parts.get(i))) {
						matched = false;
						break;
					}
				}
			} else {
				matched = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
