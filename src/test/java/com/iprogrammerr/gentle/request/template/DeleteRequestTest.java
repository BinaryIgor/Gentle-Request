package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.EmptyRequestThatHasProperValues;
import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.RequestThatCanHaveAdditionalHeaders;
import com.iprogrammerr.gentle.request.mock.MockedHeaders;

public final class DeleteRequestTest {

	@Test
	public void canHaveHeaders() {
		List<Header> headers = new MockedHeaders().mocked();
		String url = "www.mock.com";
		assertThat(new DeleteRequest(url, headers),
				new EmptyRequestThatHasProperValues("DELETE", url, headers));
	}

	@Test
	public void canHaveNoHeaders() {
		String url = "www.mock.com";
		assertThat(new DeleteRequest(url), new EmptyRequestThatHasProperValues("DELETE", url));
	}

	@Test
	public void canHaveAdditionalHeaders() {
		assertThat(new DeleteRequest("www.mock.com"),
				new RequestThatCanHaveAdditionalHeaders(new MockedHeaders().mocked()));
	}
}
