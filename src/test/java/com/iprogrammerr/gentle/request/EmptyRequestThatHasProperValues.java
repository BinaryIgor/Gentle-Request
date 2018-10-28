package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class EmptyRequestThatHasProperValues extends TypeSafeMatcher<Request> {

	private final String method;
	private final String url;
	private final List<Header> headers;

	public EmptyRequestThatHasProperValues(String method, String url, List<Header> headers) {
		this.method = method;
		this.url = url;
		this.headers = headers;
	}

	public EmptyRequestThatHasProperValues(String method, String url) {
		this(method, url, new ArrayList<>());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Request item) {
		assertThat(this.method, Matchers.equalToIgnoringCase(item.method()));
		assertThat(this.url, Matchers.equalTo(item.url()));
		assertThat(this.headers, Matchers
				.containsInAnyOrder(item.headers().toArray(new Header[item.headers().size()])));
		boolean matched;
		try {
			assertThat(item.body().length, Matchers.equalTo(0));
			matched = true;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

}
