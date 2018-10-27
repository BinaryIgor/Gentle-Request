package com.iprogrammerr.gentle.request.multipart;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class HttpFormPartTest {

	@Test
	public void canReadAndWriteText() throws Exception {
		String name = "secret";
		assertThat(new HttpFormPart(name, name),
				new HttpFormPartThatIsReadingAndWriting(name.getBytes(), name));
	}

	@Test
	public void canReadAndWriteBinary() throws Exception {
		String name = "java";
		byte[] content = new MockedBinary().content();
		String filename = "java.png";
		String contentType = "image/png";
		assertThat(new HttpFormPart(name, filename, contentType, content),
				new HttpFormPartThatIsReadingAndWriting(content, contentType, name, filename));
	}
}
