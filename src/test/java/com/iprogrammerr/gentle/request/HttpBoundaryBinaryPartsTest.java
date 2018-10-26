package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;
import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class HttpBoundaryBinaryPartsTest {

    @Test
    public void canSplit() throws Exception {
	List<FormPart> parts = new ArrayList<>();
	parts.add(new HttpFormPart("secret", "secret"));
	parts.add(new HttpFormPart("secret2", "secret.png", "image/png", new MockedBinary().content()));
	String boundary = "abcde6";
	HttpMultipartForm multipartForm = new HttpMultipartForm(boundary, parts);
	assertThat(new HttpBoundaryBinaryParts("--" + boundary), new SplitableBinaryParts(multipartForm));
    }

    private final class SplitableBinaryParts extends TypeSafeMatcher<HttpBoundaryBinaryParts> {

	private final MultipartForm toSplit;

	public SplitableBinaryParts(MultipartForm toSplit) {
	    this.toSplit = toSplit;
	}

	@Override
	public void describeTo(Description description) {
	    description.appendText(String.format("HttpBoundaryBinaryParts that has %d parts", toSplit.parts().size()));
	}

	@Override
	protected boolean matchesSafely(HttpBoundaryBinaryParts item) {
	    boolean matched = true;
	    try {
		List<FormPart> sourceParts = this.toSplit.parts();
		List<byte[]> parts = item.parts(this.toSplit.body());
		for (int i = 0; i < parts.size(); ++i) {
		    if (!Arrays.equals(parts.get(i), sourceParts.get(i).parsed())) {
			matched = false;
			break;
		    }
		}
	    } catch (Exception e) {
		matched = false;
	    }
	    return matched;
	}

    }
}
