package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class RequestThatCanHaveAdditionalHeaders extends TypeSafeMatcher<Request> {

	private final List<Header> toAdd;

	public RequestThatCanHaveAdditionalHeaders(List<Header> toAdd) {
		this.toAdd = toAdd;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Request item) {
		for (Header h : this.toAdd) {
			item.addHeader(h);
		}
		assertThat(item.headers(),
				Matchers.hasItems(this.toAdd.toArray(new Header[this.toAdd.size()])));
		return true;
	}

}
