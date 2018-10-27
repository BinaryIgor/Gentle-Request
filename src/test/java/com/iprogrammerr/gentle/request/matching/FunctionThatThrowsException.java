package com.iprogrammerr.gentle.request.matching;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class FunctionThatThrowsException extends TypeSafeMatcher<UnreliableFunction> {

	@Override
	public void describeTo(Description description) {
		description.appendText("Callable that throws Exception");
	}

	@Override
	protected boolean matchesSafely(UnreliableFunction item) {
		boolean thrown;
		try {
			item.apply();
			thrown = false;
		} catch (Exception e) {
			thrown = true;
		}
		return thrown;
	}
}
