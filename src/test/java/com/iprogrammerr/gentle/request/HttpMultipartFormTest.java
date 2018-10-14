package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;

public final class HttpMultipartFormTest {

    @Test
    public void canReadAndWrite() throws Exception {
	List<FormPart> parts = new ArrayList<>();
	FormPart firstPart = new HttpFormPart("test", "test");
	parts.add(firstPart);
	String boundary = "abcde";
	HttpMultipartForm multipart = new HttpMultipartForm(boundary, parts);
	Header properHeader = properHeader(boundary);
	assertTrue(multipart.header().equals(properHeader));
	HttpMultipartForm parsedMultipart = new HttpMultipartForm(boundary, multipart.body());
	assertTrue(multipart.header().equals(parsedMultipart.header()));
	partsShouldBeEqual(parts.iterator(), parsedMultipart.parts().iterator());
	parts.clear();
	firstPart = new HttpFormPart("json", "json.json", "application/json", "{\"secret\": true}".getBytes());
	FormPart secondPart = new HttpFormPart("image", "java.png", "image/png", new MockedBinary().content());
	parts.add(firstPart);
	parts.add(secondPart);
	boundary = "2ddd55g";
	multipart = new HttpMultipartForm(boundary, parts);
	properHeader = properHeader(boundary);
	assertTrue(multipart.header().equals(properHeader));
	parsedMultipart = new HttpMultipartForm(boundary, multipart.body());
	assertTrue(multipart.header().equals(parsedMultipart.header()));
	partsShouldBeEqual(parts.iterator(), parsedMultipart.parts().iterator());
    }

    private Header properHeader(String boundary) {
	return new Header("Content-Type", "multipart/form-data; boundary=" + boundary);
    }

    private void partsShouldBeEqual(Iterator<FormPart> first, Iterator<FormPart> second) throws Exception {
	while (first.hasNext()) {
	    FormPart fp = first.next();
	    FormPart sp = second.next();
	    assertTrue(fp.name().equals(sp.name()));
	    assertTrue(fp.contentType().equals(sp.contentType()));
	    assertTrue(fp.filename().equals(sp.filename()));
	    assertTrue(Arrays.equals(fp.content(), sp.content()));
	}
    }
}
