package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;

public final class StartEndBinaryPartsTest {

	@Test
	public void canSplit() throws Exception {
		byte[] start = "start".getBytes();
		byte[] end = "end".getBytes();
		List<byte[]> parts = new ArrayList<>();
		MockedBinary binary = new MockedBinary(100, 10_000);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int size = 1 + (int) (Math.random() * 10);
		for (int i = 0; i < size; ++i) {
			baos.write(start);
			parts.add(binary.content());
			baos.write(parts.get(i));
			baos.write(end);
		}
		assertThat(new StartEndBinaryParts(start, end),
				new BinaryPartsThatContainParts(baos.toByteArray(), parts));
	}
}