package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;

public final class HttpBoundaryBinaryPartsTest {

	@Test
	public void canSplit() throws Exception {
		List<FormPart> parts = new ArrayList<>();
		parts.add(new HttpFormPart("secret", "secret"));
		parts.add(new HttpFormPart("secret2", "secret.png", "image/png",
				new MockedBinary().content()));
		HttpMultipartForm form = new HttpMultipartForm(parts);
		assertThat(new HttpBoundaryBinaryParts("--" + form.boundary()),
				new BinaryPartsThatHasGivenParts(form.body(), form.parts()));
	}
}
