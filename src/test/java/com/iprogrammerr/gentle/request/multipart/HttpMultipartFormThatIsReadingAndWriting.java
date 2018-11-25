package com.iprogrammerr.gentle.request.multipart;

import java.util.Arrays;
import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.template.MultipartContentTypeHeader;

public final class HttpMultipartFormThatIsReadingAndWriting extends TypeSafeMatcher<HttpMultipartForm> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(HttpMultipartForm item) {
		boolean matched;
		try {
			matched = item.header().equals(new MultipartContentTypeHeader(item.boundary()));
			if (matched) {
				matched = partsAreEqual(item.parts().iterator(),
						new HttpMultipartForm(item.boundary(), item.body()).parts().iterator());
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

	private boolean partsAreEqual(Iterator<FormPart> first, Iterator<FormPart> second) throws Exception {
		boolean equal = true;
		while (first.hasNext()) {
			FormPart fp = first.next();
			FormPart sp = second.next();
			equal = fp.name().equals(sp.name()) && fp.contentType().equals(sp.contentType())
					&& fp.filename().equals(sp.filename()) && Arrays.equals(fp.content(), sp.content());
			if (!equal) {
				break;
			}
		}
		return equal;
	}
}
