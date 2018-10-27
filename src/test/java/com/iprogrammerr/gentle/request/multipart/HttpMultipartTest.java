package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class HttpMultipartTest {

	@Test
	public void canReadAndWriteSingle() throws Exception {
		String boundary = "abcde";
		String type = "mixed";
		HttpMultipart multipart = new HttpMultipart(type, boundary,
				Collections.singletonList(new HttpPart("test")));
		assertThat(multipart, new HttpMultipartThatIsReadingAndWriting(type, boundary));
	}

	@Test
	public void canReadAndWriteMultiple() throws Exception {
		List<Part> parts = new ArrayList<>();
		parts.add(new HttpPart("application/json", "{\"secret\":true}".getBytes()));
		parts.add(new HttpPart("image/png", new MockedBinary().content()));
		String boundary = "2ddd55g";
		String type = "alternative";
		assertThat(new HttpMultipart(type, boundary, parts),
				new HttpMultipartThatIsReadingAndWriting(type, boundary));
	}
}
