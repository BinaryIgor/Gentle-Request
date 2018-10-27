package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.Header;

public final class HttpMultipartThatIsReadingAndWriting extends TypeSafeMatcher<HttpMultipart> {

	private final String type;

	public HttpMultipartThatIsReadingAndWriting(String type) {
		this.type = type;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(HttpMultipart item) {
		boolean matched;
		try {
			assertTrue(item.header().equals(properHeader(this.type, item.boundary())));
			HttpMultipart parsed = new HttpMultipart(this.type, item.boundary(), item.body());
			assertTrue(item.header().equals(parsed.header()));
			partsShouldBeEqual(item.parts().iterator(), parsed.parts().iterator());
			matched = true;
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private Header properHeader(String type, String boundary) {
		return new Header("Content-Type",
				String.format("multipart/%s; boundary=%s", type, boundary));
	}

	private void partsShouldBeEqual(Iterator<Part> first, Iterator<Part> second) throws Exception {
		while (first.hasNext()) {
			Part fp = first.next();
			Part sp = second.next();
			assertTrue(fp.contentType().equals(sp.contentType()));
			assertTrue(Arrays.equals(fp.content(), sp.content()));
		}
	}

}
