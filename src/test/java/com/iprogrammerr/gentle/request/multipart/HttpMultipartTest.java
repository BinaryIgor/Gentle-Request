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
		String type = "mixed";
		HttpMultipart multipart = new HttpMultipart(type,
				Collections.singletonList(new HttpPart("test")));
		assertThat(multipart, new HttpMultipartThatIsReadingAndWriting(type));
	}

	@Test
	public void canReadAndWriteMultiple() throws Exception {
		List<Part> parts = new ArrayList<>();
		parts.add(new HttpPart("application/json", "{\"secret\":true}".getBytes()));
		parts.add(new HttpPart("image/png", new MockedBinary().content()));
		String type = "alternative";
		assertThat(new HttpMultipart(type, parts), new HttpMultipartThatIsReadingAndWriting(type));
	}
}
