package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class ResponseThatIsRequest extends TypeSafeMatcher<Response> {

	private final int code;
	private final Request request;

	public ResponseThatIsRequest(int code, Request request) {
		this.code = code;
		this.request = request;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected boolean matchesSafely(Response item) {
		boolean matched;
		assertThat(this.code, Matchers.equalTo(item.code()));
		try {
			assertTrue(Arrays.equals(this.request.body(), item.body().value()));
			assertThat(item.headers(), Matchers.hasItems(
					this.request.headers().toArray(new Header[this.request.headers().size()])));
			matched = true;
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
