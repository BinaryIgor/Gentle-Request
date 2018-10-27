package com.iprogrammerr.gentle.request.matching;

import java.util.concurrent.Callable;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ThrowsExceptions extends TypeSafeMatcher<Iterable<Callable<Object>>> {

	@Override
	public void describeTo(Description description) {
		description.appendText("Callables that throws Exceptions");
	}

	@Override
	protected boolean matchesSafely(Iterable<Callable<Object>> items) {
		boolean matched = true;
		for (Callable<Object> item : items) {
			try {
				item.call();
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
