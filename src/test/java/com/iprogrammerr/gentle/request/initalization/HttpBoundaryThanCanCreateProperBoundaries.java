package com.iprogrammerr.gentle.request.initalization;

import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.initialization.HttpBoundary;

public final class HttpBoundaryThanCanCreateProperBoundaries extends TypeSafeMatcher<HttpBoundary> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected boolean matchesSafely(HttpBoundary item) {
		for (int i = 0; i < 10; ++i) {
			String boundary = item.value();
			assertThat(boundary, Matchers.matchesPattern("^[a-zA-Z0-9]*$"));
			assertThat(boundary.length(),
					Matchers.allOf(Matchers.greaterThanOrEqualTo(5), Matchers.lessThan(70)));
		}
		return true;
	}
}
