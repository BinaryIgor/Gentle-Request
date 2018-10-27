package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class HttpPartTest {

	@Test
	public void canReadAndWriteText() throws Exception {
		String content = "content";
		String contentType = "text/plain";
		assertThat(new HttpPart(content),
				new HttpPartThatIsReadingAndWriting(content.getBytes(), contentType));
	}

	@Test
	public void canReadAndWriteBinary() throws Exception {
		byte[] content = new MockedBinary().content();
		String contentType = "image/png";
		assertThat(new HttpPart(contentType, content),
				new HttpPartThatIsReadingAndWriting(content, contentType));

	}
}
