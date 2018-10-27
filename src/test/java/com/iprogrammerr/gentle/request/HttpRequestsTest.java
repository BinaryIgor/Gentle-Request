package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

import com.iprogrammerr.gentle.request.matching.ThrowsExceptions;

public final class HttpRequestsTest {

	@Test
	public void canReadResponses() throws Exception {
		int port = availablePort();
		assertThat(new HttpRequests(), new RequestsThatAreResponding(port));
	}

	private int availablePort() throws Exception {
		try (ServerSocket s = new ServerSocket(0)) {
			return s.getLocalPort();
		}
	}

	@Test
	public void shouldNotAcceptBadProtocols() {
		String badProtocolUrl = "https://jsonplaceholder.typicode.com/posts".replace("http", "abc");
		Requests requests = new HttpRequests();
		List<Callable<Object>> callables = Arrays.asList(() -> requests.getResponse(badProtocolUrl),
				() -> requests.postResponse(badProtocolUrl, new byte[0]),
				() -> requests.postResponse(badProtocolUrl),
				() -> requests.postResponse(badProtocolUrl),
				() -> requests.putResponse(badProtocolUrl, new byte[0]),
				() -> requests.deleteResponse(badProtocolUrl),
				() -> requests.methodResponse("head", badProtocolUrl));
		assertThat(callables, new ThrowsExceptions());
	}
}
