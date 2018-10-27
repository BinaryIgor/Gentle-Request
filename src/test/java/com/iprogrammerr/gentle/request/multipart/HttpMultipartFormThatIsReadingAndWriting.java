package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.Header;

public final class HttpMultipartFormThatIsReadingAndWriting extends TypeSafeMatcher<HttpMultipartForm> {

	private final String boundary;

	public HttpMultipartFormThatIsReadingAndWriting(String boundary) {
		this.boundary = boundary;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(HttpMultipartForm item) {
		boolean matched;
		try {
			assertTrue(item.header().equals(properHeader(this.boundary)));
			HttpMultipartForm parsed = new HttpMultipartForm(this.boundary, item.body());
			partsShouldBeEqual(item.parts().iterator(), parsed.parts().iterator());
			matched = true;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

	private Header properHeader(String boundary) {
		return new Header("Content-Type", "multipart/form-data; boundary=" + boundary);
	}

	private void partsShouldBeEqual(Iterator<FormPart> first, Iterator<FormPart> second)
			throws Exception {
		while (first.hasNext()) {
			FormPart fp = first.next();
			FormPart sp = second.next();
			assertTrue(fp.name().equals(sp.name()));
			assertTrue(fp.contentType().equals(sp.contentType()));
			assertTrue(fp.filename().equals(sp.filename()));
			assertTrue(Arrays.equals(fp.content(), sp.content()));
		}
	}
}
