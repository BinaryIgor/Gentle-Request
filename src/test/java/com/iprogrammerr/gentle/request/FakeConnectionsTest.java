package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.gentle.request.template.GetRequest;

public final class FakeConnectionsTest {

	@Test
	public void shouldReturnInput() throws Exception {
		Response response = new HttpResponse(200);
		assertThat(response, Matchers.equalTo(new Connections.Fake(response).response(new GetRequest("www.mock.com"))));
	}
}
