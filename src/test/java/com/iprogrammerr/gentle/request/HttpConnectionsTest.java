package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.matching.FunctionsThatThrowsExceptions;
import com.iprogrammerr.gentle.request.matching.UnreliableFunction;
import com.iprogrammerr.gentle.request.template.DeleteRequest;
import com.iprogrammerr.gentle.request.template.GetRequest;
import com.iprogrammerr.gentle.request.template.PostRequest;
import com.iprogrammerr.gentle.request.template.PutRequest;

public final class HttpConnectionsTest {

	@Test
	public void canSendAndReceive() throws Exception {
		int port = availablePort();
		assertThat(new HttpConnections(), new ConnectionsThatAreSendingAndResponding(port));
	}

	private int availablePort() throws Exception {
		try (ServerSocket s = new ServerSocket(0)) {
			return s.getLocalPort();
		}
	}

	@Test
	public void shouldNotAcceptBadProtocols() {
		String badProtocolUrl = "https://jsonplaceholder.typicode.com/posts".replace("http", "abc");
		Connections connections = new HttpConnections();
		List<UnreliableFunction> executables = Arrays.asList(
				() -> connections.response(new GetRequest(badProtocolUrl)),
				() -> connections.response(new PostRequest(badProtocolUrl, new byte[0])),
				() -> connections.response(new PostRequest(badProtocolUrl)),
				() -> connections.response(new PutRequest(badProtocolUrl, new byte[0])),
				() -> connections.response(new DeleteRequest(badProtocolUrl)),
				() -> connections.response(new EmptyRequest("head", badProtocolUrl)));
		assertThat(executables, new FunctionsThatThrowsExceptions());
	}
}
