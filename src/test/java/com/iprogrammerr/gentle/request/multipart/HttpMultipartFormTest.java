package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class HttpMultipartFormTest {

	@Test
	public void canReadAndWriteSingle() throws Exception {
		String boundary = "abcde";
		assertThat(
				new HttpMultipartForm(boundary,
						Collections.singletonList(new HttpFormPart("test", "test"))),
				new HttpMultipartFormThatIsReadingAndWriting(boundary));
	}

	@Test
	public void canReadAndWriteMultiple() throws Exception {
		List<FormPart> parts = new ArrayList<>();
		parts.add(new HttpFormPart("json", "json.json", "application/json",
				"{\"secret\": true}".getBytes()));
		parts.add(new HttpFormPart("image", "java.png", "image/png", new MockedBinary().content()));
		String boundary = "2ddd55g";
		assertThat(new HttpMultipartForm(boundary, parts),
				new HttpMultipartFormThatIsReadingAndWriting(boundary));
	}
}
