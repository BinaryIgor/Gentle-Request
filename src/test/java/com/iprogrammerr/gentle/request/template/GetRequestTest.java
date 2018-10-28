package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.EmptyRequestThatHasProperValues;
import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.RequestThatCanHaveAdditionalHeaders;
import com.iprogrammerr.gentle.request.mock.MockedHeaders;
import com.iprogrammerr.gentle.request.template.GetRequest;

public final class GetRequestTest {

	@Test
	public void canHaveHeaders() {
		List<Header> headers = new MockedHeaders().mocked();
		String url = "www.mock.com";
		assertThat(new GetRequest(url, headers),
				new EmptyRequestThatHasProperValues("GET", url, headers));
	}

	@Test
	public void canHaveNoHeaders() {
		String url = "www.mock.com";
		assertThat(new GetRequest(url), new EmptyRequestThatHasProperValues("GET", url));
	}

	@Test
	public void canHaveAdditionalHeaders() {
		assertThat(new GetRequest("www.mock.com"),
				new RequestThatCanHaveAdditionalHeaders(new MockedHeaders().mocked()));
	}
}
