package com.iprogrammerr.gentle.request;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HeaderThatContainsValues extends TypeSafeMatcher<Header> {

	private final String key;
	private final String value;

	public HeaderThatContainsValues(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Header item) {
		return this.key.equalsIgnoreCase(item.key()) && this.value.equals(item.value())
				&& item.is(this.key);
	}
}
