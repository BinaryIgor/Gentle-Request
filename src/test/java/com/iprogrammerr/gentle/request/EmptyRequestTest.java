package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedHeaders;

public final class EmptyRequestTest {

	@Test
	public void canHaveHeaders() {
		List<Header> headers = new MockedHeaders().mocked();
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

	@Test
	public void canHaveAdditionalHeaders() {
		assertThat(new EmptyRequest("Post", "www.mock.com"),
				new RequestThatCanHaveAdditionalHeaders(new MockedHeaders().mocked()));
	}
}
