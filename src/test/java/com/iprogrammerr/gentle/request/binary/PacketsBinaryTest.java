package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class PacketsBinaryTest {

	@Test
	public void canReadContent() throws Exception {
		byte[] content = new MockedBinary(1_000_000, 10_000_000).content();
		try (BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
			assertThat(new PacketsBinary(is, content.length), new BinaryThatContains(content));
		}
	}
}
