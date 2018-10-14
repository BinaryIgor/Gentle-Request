package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.HttpPart;

public final class HttpPartTest {

    @Test
    public void canReadAndWrite() throws Exception {
	String content = "content";
	HttpPart part = new HttpPart(content);
	byte[] parsed = part.parsed();
	HttpPart parsedPart = new HttpPart(parsed);
	assertTrue(Arrays.equals(parsed, parsedPart.parsed()));
	assertTrue(Arrays.equals(content.getBytes(), parsedPart.content()));
	byte[] rawContent = new MockedBinary().content();
	String contentType = "image/png";
	part = new HttpPart(contentType, rawContent);
	parsed = part.parsed();
	parsedPart = new HttpPart(parsed);
	assertTrue(Arrays.equals(parsed, parsedPart.parsed()));
	assertTrue(Arrays.equals(rawContent, parsedPart.content()));
	assertTrue(parsedPart.contentType().equals(contentType));
    }
}
