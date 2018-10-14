package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.HttpMultipart;
import com.iprogrammerr.gentle.request.multipart.HttpPart;
import com.iprogrammerr.gentle.request.multipart.Part;

public final class HttpMultipartTest {

    @Test
    public void canReadAndWrite() throws Exception {
	List<Part> parts = new ArrayList<>();
	Part firstPart = new HttpPart("test");
	parts.add(firstPart);
	String boundary = "abcde";
	String type = "mixed";
	HttpMultipart multipart = new HttpMultipart(type, boundary, parts);
	Header properHeader = properHeader(type, boundary);
	assertTrue(multipart.header().equals(properHeader));
	HttpMultipart parsedMultipart = new HttpMultipart(type, boundary, multipart.body());
	assertTrue(multipart.header().equals(parsedMultipart.header()));
	partsShouldBeEqual(parts.iterator(), parsedMultipart.parts().iterator());
	parts.clear();
	firstPart = new HttpPart("application/json", "{\"secret\": true}".getBytes());
	Part secondPart = new HttpPart("image/png", new MockedBinary().content());
	parts.add(firstPart);
	parts.add(secondPart);
	boundary = "2ddd55g";
	multipart = new HttpMultipart(type, boundary, parts);
	properHeader = properHeader(type, boundary);
	assertTrue(multipart.header().equals(properHeader));
	parsedMultipart = new HttpMultipart(type, boundary, multipart.body());
	assertTrue(multipart.header().equals(parsedMultipart.header()));
	partsShouldBeEqual(parts.iterator(), parsedMultipart.parts().iterator());
    }

    private void partsShouldBeEqual(Iterator<Part> first, Iterator<Part> second) throws Exception {
	while (first.hasNext()) {
	    Part fp = first.next();
	    Part sp = second.next();
	    assertTrue(fp.contentType().equals(sp.contentType()));
	    assertTrue(Arrays.equals(fp.content(), sp.content()));
	}
    }

    private Header properHeader(String type, String boundary) {
	return new Header("Content-Type", String.format("multipart/%s; boundary=%s", type, boundary));
    }
}
