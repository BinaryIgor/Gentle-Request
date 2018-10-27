package com.iprogrammerr.gentle.request.matching;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class FunctionsThatThrowsExceptions extends TypeSafeMatcher<Iterable<UnreliableFunction>> {

	@Override
	public void describeTo(Description description) {
		description.appendText("Callables that throws Exceptions");
	}

	@Override
	protected boolean matchesSafely(Iterable<UnreliableFunction> items) {
		boolean matched = true;
		for (UnreliableFunction item : items) {
			try {
				item.apply();
				matched = false;
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
