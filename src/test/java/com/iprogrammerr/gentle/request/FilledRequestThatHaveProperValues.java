package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.template.ContentLengthHeader;
import com.iprogrammerr.gentle.request.template.ContentTypeHeader;

public final class FilledRequestThatHaveProperValues extends TypeSafeMatcher<Request> {

	private final String method;
	private final String url;
	private final List<Header> headers;
	private final Header contentType;
	private final byte[] body;

	public FilledRequestThatHaveProperValues(String method, String url, List<Header> headers,
			Header contentType, byte[] body) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.contentType = contentType;
		this.body = body;
	}

	public FilledRequestThatHaveProperValues(String method, String url, Header contentType,
			byte[] body) {
		this(method, url, new ArrayList<>(), contentType, body);
	}

	public FilledRequestThatHaveProperValues(String method, String url, List<Header> headers,
			byte[] body) {
		this(method, url, headers, new ContentTypeHeader("application/x-www-form-urlencoded"),
				body);
	}

	public FilledRequestThatHaveProperValues(String method, String url, byte[] body) {
		this(method, url, new ArrayList<>(), body);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Request item) {
		assertThat(item.method(), Matchers.equalToIgnoringCase(this.method));
		assertThat(item.url(), Matchers.equalTo(this.url));
		assertThat(this.headers.size(), Matchers.equalTo(item.headers().size() - 2));
		assertThat(item.headers(),
				Matchers.hasItems(this.contentType, new ContentLengthHeader(this.body.length)));
		boolean matched;
		try {
			assertTrue(Arrays.equals(this.body, item.body()));
			matched = true;
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

}
