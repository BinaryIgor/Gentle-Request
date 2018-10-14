package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;

public final class HttpFormPartTest {

    @Test
    public void canReadAndWrite() throws Exception {
	String name = "secret";
	byte[] content = name.getBytes();
	HttpFormPart toParsePart = new HttpFormPart(name, name);
	byte[] parsed = toParsePart.parsed();
	HttpFormPart parsedPart = new HttpFormPart(parsed);
	assertTrue(Arrays.equals(parsed, parsedPart.parsed()));
	assertTrue(name.equals(parsedPart.name()));
	assertTrue(Arrays.equals(content, parsedPart.content()));
	name = "java";
	content = new MockedBinary().content();
	String filename = "java.png";
	String contentType = "image/png";
	toParsePart = new HttpFormPart(name, filename, contentType, content);
	parsed = toParsePart.parsed();
	parsedPart = new HttpFormPart(parsed);
	assertTrue(Arrays.equals(parsed, parsedPart.parsed()));
	assertTrue(name.equals(parsedPart.name()));
	assertTrue(filename.equals(parsedPart.filename()));
	assertTrue(contentType.equals(parsedPart.contentType()));
	assertTrue(Arrays.equals(content, parsedPart.content()));
    }
}
