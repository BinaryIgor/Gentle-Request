package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.initialization.FileContent;
import com.iprogrammerr.gentle.request.matching.FunctionThatThrowsException;
import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.mock.MockedHeaders;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;
import com.iprogrammerr.gentle.request.multipart.HttpPart;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;
import com.iprogrammerr.gentle.request.template.ContentTypeHeader;

public final class FilledRequestTest {

	private static final String URL = "www.mock.com";

	@Test
	public void canContainBytes() throws Exception {
		String method = "post";
		byte[] content = new MockedBinary().content();
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new FilledRequest(method, URL, headers, content),
				new FilledRequestThatHasProperValues(method, URL, headers, content));
	}

	@Test
	public void canContainText() throws Exception {
		String method = "put";
		String content = "Text";
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new FilledRequest(method, URL, headers, content),
				new FilledRequestThatHasProperValues(method, URL, headers,
						new ContentTypeHeader("text/plain"), content.getBytes()));
	}

	@Test
	public void canContainJson() throws Exception {
		String method = "get";
		JSONObject json = new JSONObject();
		json.put("id", 22);
		json.put("name", "Igor");
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(
				new FilledRequest(method, URL, json, headers.toArray(new Header[headers.size()])),
				new FilledRequestThatHasProperValues(method, URL, headers,
						new ContentTypeHeader("application/json"), json.toString().getBytes()));
	}

	@Test
	public void canContainFile() throws Exception {
		String method = "put";
		File file = new File(String.format("src%stest%sresources%sjava.png", File.separator,
				File.separator, File.separator));
		String type = "image/jpg";
		List<Header> headers = new MockedHeaders().mocked();
		byte[] content = new FileContent(file).value();
		assertThat(
				new FilledRequest(method, URL, type, file,
						headers.toArray(new Header[headers.size()])),
				new FilledRequestThatHasProperValues(method, URL, headers,
						new ContentTypeHeader(type), content));
	}

	@Test
	public void canContainMultipart() throws Exception {
		String method = "post";
		Multipart multipart = new HttpMultipart("alternative",
				new HttpPart("application/json", "{\"secret\":true}".getBytes()),
				new HttpPart("image/png", new MockedBinary().content()));
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new FilledRequest(method, URL, headers, multipart),
				new FilledRequestThatHasProperValues(method, URL, headers, multipart.header(),
						multipart.body()));
	}

	@Test
	public void canContainMultipartForm() throws Exception {
		String method = "put";
		MultipartForm multipart = new HttpMultipartForm(
				new HttpFormPart("json", "json.json", "application/json",
						"{\"secret\": false}".getBytes()),
				new HttpFormPart("image", "java.png", "image/png", new MockedBinary().content()));
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new FilledRequest(method, URL, headers, multipart),
				new FilledRequestThatHasProperValues(method, URL, headers, multipart.header(),
						multipart.body()));
	}

	@Test
	public void shouldFailIfBodyIsNotValid() {
		FilledRequest request = new FilledRequest("put", URL, "unknown", new File("??"));
		assertThat(() -> request.body(), new FunctionThatThrowsException());
	}
}
