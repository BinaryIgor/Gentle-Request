package com.iprogrammerr.gentle.request.multipart;

import java.util.Arrays;
import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.template.MultipartContentTypeHeader;

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
			matched = item.header().equals(new MultipartContentTypeHeader(this.type, item.boundary()));
			HttpMultipart parsed = new HttpMultipart(this.type, item.boundary(), item.body());
			if (matched) {
				matched = item.header().equals(parsed.header())
						&& partsAreEqual(item.parts().iterator(), parsed.parts().iterator());
			}
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private boolean partsAreEqual(Iterator<Part> first, Iterator<Part> second) throws Exception {
		boolean equal = true;
		while (first.hasNext()) {
			Part fp = first.next();
			Part sp = second.next();
			equal = fp.contentType().equals(sp.contentType()) && Arrays.equals(fp.content(), sp.content());
			if (!equal) {
				break;
			}
		}
		return equal;
	}
}
