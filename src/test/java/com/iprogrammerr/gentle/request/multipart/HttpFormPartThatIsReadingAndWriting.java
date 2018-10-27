package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HttpFormPartThatIsReadingAndWriting extends TypeSafeMatcher<HttpFormPart> {

	private final byte[] content;
	private final String contentType;
	private final String name;
	private final String filename;

	public HttpFormPartThatIsReadingAndWriting(byte[] content, String contentType, String name,
			String filename) {
		this.content = content;
		this.contentType = contentType;
		this.name = name;
		this.filename = filename;
	}

	public HttpFormPartThatIsReadingAndWriting(byte[] content, String name) {
		this(content, "text/plain", name, "");
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(HttpFormPart item) {
		boolean matched;
		try {
			byte[] parsed = item.parsed();
			HttpFormPart parsedPart = new HttpFormPart(parsed);
			assertTrue(Arrays.equals(parsed, parsedPart.parsed()));
			assertTrue(this.contentType.equals(parsedPart.contentType())
					&& this.name.equals(parsedPart.name())
					&& this.filename.equals(item.filename()));
			assertTrue(Arrays.equals(content, parsedPart.content()));
			matched = true;
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
