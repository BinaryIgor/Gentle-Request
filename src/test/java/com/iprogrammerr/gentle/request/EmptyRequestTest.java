package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.template.AuthorizationHeader;
import com.iprogrammerr.gentle.request.template.MultipartContentTypeHeader;

public final class EmptyRequestTest {

	@Test
	public void canHaveHeaders() {
		List<Header> headers = new ArrayList<>();
		headers.add(new MultipartContentTypeHeader("abcdefg"));
		headers.add(new AuthorizationHeader("SECRET"));
		headers.add(new HttpHeader("Content-Length", "55"));
		String method = "get";
		String url = "www.mock.com";
		assertThat(new EmptyRequest(method, url, headers),
				new EmptyRequestThatHasProperValues(method, url, headers));
	}

	@Test
	public void canHaveNoHeaders() {
		String method = "POST";
		String url = "www.mock.com";
		assertThat(new EmptyRequest(method, url), new EmptyRequestThatHasProperValues(method, url));
	}
}
