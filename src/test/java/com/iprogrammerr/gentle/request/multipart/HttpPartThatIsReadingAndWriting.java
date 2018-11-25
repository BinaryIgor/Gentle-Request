package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertTrue;

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
			byte[] parsed = item.source();
			HttpPart parsedPart = new HttpPart(parsed);
			assertTrue(Arrays.equals(parsed, parsedPart.source()));
			assertTrue(Arrays.equals(this.content, parsedPart.content()));
			assertTrue(parsedPart.contentType().equals(this.contentType));
			matched = true;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
