package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.matching.FunctionsThatThrowsExceptions;
import com.iprogrammerr.gentle.request.matching.UnreliableFunction;

public final class HttpRequestsTest {

	@Test
	public void canSendAndReceive() throws Exception {
		int port = availablePort();
		assertThat(new HttpRequests(), new RequestsThatAreSendingAndResponding(port));
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
		List<UnreliableFunction> executables = Arrays.asList(
				() -> requests.getResponse(badProtocolUrl),
				() -> requests.postResponse(badProtocolUrl, new byte[0]),
				() -> requests.postResponse(badProtocolUrl),
				() -> requests.putResponse(badProtocolUrl, new byte[0]),
				() -> requests.deleteResponse(badProtocolUrl),
				() -> requests.methodResponse("head", badProtocolUrl));
		assertThat(executables, new FunctionsThatThrowsExceptions());
	}
}
