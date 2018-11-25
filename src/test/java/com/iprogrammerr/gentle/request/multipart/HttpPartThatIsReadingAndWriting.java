package com.iprogrammerr.gentle.request.multipart;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HttpPartThatIsReadingAndWriting extends TypeSafeMatcher<HttpPart> {

	private final byte[] content;
	private final String contentType;

	public HttpPartThatIsReadingAndWriting(byte[] content, String contentType) {
		this.content = content;
		this.contentType = contentType;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected boolean matchesSafely(HttpPart item) {
		boolean matched;
		try {
			byte[] source = item.source();
			HttpPart raw = new HttpPart(source);
			matched = Arrays.equals(source, raw.source()) && Arrays.equals(this.content, raw.content())
					&& raw.contentType().equals(this.contentType);
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
