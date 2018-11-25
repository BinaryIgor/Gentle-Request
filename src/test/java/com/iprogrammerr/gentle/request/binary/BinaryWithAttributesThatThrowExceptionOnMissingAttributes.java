package com.iprogrammerr.gentle.request.binary;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryWithAttributesThatThrowExceptionOnMissingAttributes extends TypeSafeMatcher<BinaryWithAttributes> {

	private final List<String> missing;

	public BinaryWithAttributesThatThrowExceptionOnMissingAttributes(List<String> missing) {
		this.missing = missing;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryWithAttributes item) {
		boolean matched = true;
		for (String m : this.missing) {
			try {
				matched = false;
				item.attribute(m);
			} catch (Exception e) {
				matched = true;
			}
			if (!matched) {
				break;
			}
		}
		return matched;
	}
}
