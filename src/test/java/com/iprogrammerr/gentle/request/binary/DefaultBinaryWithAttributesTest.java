package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class DefaultBinaryWithAttributesTest {

	@Test
	public void canHaveProperValues() throws Exception {
		byte[] content = new MockedBinary().content();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("contentType", "raw");
		attributes.put("contentLength", String.valueOf(content.length));
		assertThat(new DefaultBinaryWithAttributes(new OnePacketBinary(new ByteArrayInputStream(content)), attributes),
				new BinaryWithAttributesThatHaveProperValues(content, attributes));
	}

	@Test
	public void shouldThrowExceptionOnMissingAttributes() {
		assertThat(new DefaultBinaryWithAttributes(new byte[0], new HashMap<>()),
				new BinaryWithAttributesThatThrowExceptionOnMissingAttributes(
						Arrays.asList("contentType", "secret", "secret2")));
	}
}
