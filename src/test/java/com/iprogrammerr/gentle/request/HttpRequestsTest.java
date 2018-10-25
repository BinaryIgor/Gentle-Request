package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.net.ServerSocket;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.DeleteMethod;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.HeadMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.PutMethod;
import com.iprogrammerr.bright.server.respondent.PotentialRespondent;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.template.BadRequestResponse;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.gentle.request.exception.ToCatchException;
import com.iprogrammerr.gentle.request.mock.MockedServer;

public final class HttpRequestsTest {

    @Test
    public void shouldNotAcceptBadProtocols() {
	ToCatchException toCatch = new ToCatchException();
	String badProtocolUrl = "https://jsonplaceholder.typicode.com/posts".replace("http", "abc");
	Requests requests = new HttpRequests();
	assertTrue(toCatch.hasCatched(() -> requests.getResponse(badProtocolUrl)));
	assertTrue(toCatch.hasCatched(() -> requests.postResponse(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> requests.putResponse(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> requests.deleteResponse(badProtocolUrl)));
    }

    @Test
    public void canHandleProperRequest() throws Exception {
	int port = availablePort();
	String baseUrl = "http://localhost:" + port + "/";
	String hello = "hello";
	Respondent mirror = req -> new OkResponse(new String(req.body()));
	try (MockedServer server = new MockedServer(port, new PotentialRespondent(hello, new GetMethod(), mirror),
		new PotentialRespondent(hello, new PostMethod(), mirror),
		new PotentialRespondent(hello, new PutMethod(), mirror),
		new PotentialRespondent(hello, new DeleteMethod(), mirror),
		new PotentialRespondent(hello, new HeadMethod(), mirror))) {
	    server.start();
	    Requests requests = new HttpRequests();
	    String target = baseUrl + hello;
	    assertTrue(requests.getResponse(target).hasProperCode());
	    String body = "body";
	    Response postResponse = requests.postResponse(target, body.getBytes());
	    assertTrue(postResponse.hasProperCode());
	    assertTrue(postResponse.body().stringValue().equals(body));
	    assertTrue(requests.postResponse(target).hasProperCode());
	    Response putResponse = requests.putResponse(target, body.getBytes());
	    assertTrue(putResponse.body().stringValue().equals(body));
	    assertTrue(putResponse.hasProperCode());
	    assertTrue(requests.deleteResponse(target).hasProperCode());
	    assertTrue(requests.methodResponse("HEAD", target).hasProperCode());
	}
    }

    @Test
    public void canReadErrors() throws Exception {
	int port = availablePort();
	String baseUrl = "http://localhost:" + port + "/";
	String error = "error";
	Respondent badRespondent = req -> new BadRequestResponse(new String(req.body()));
	Respondent internalErrorRespondent = req -> new InternalServerErrorResponse(new String(req.body()));
	try (MockedServer server = new MockedServer(port,
		new PotentialRespondent(error, new PostMethod(), badRespondent),
		new PotentialRespondent(error, new PutMethod(), internalErrorRespondent))) {
	    server.start();
	    Requests requests = new HttpRequests();
	    String target = baseUrl + error;
	    String body = "body";
	    Response postResponse = requests.postResponse(target, body.getBytes());
	    assertTrue(postResponse.code() == 400);
	    assertTrue(postResponse.body().stringValue().equals(body));
	    Response putResponse = requests.putResponse(target, body.getBytes());
	    assertTrue(putResponse.body().stringValue().equals(body));
	    assertTrue(putResponse.code() == 500);
	}
    }

    @Test
    public void canReadRedirects() throws Exception {
	int port = availablePort();
	String baseUrl = "http://localhost:" + port + "/";
	String targetUrl = "target";
	String message = "message";
	int code = 303;
	Respondent redirect = req -> {
	    return new ContentResponse(code, message);
	};
	try (MockedServer server = new MockedServer(port, new PotentialRespondent(targetUrl, new GetMethod(), redirect),
		new PotentialRespondent(targetUrl, new PostMethod(), redirect),
		new PotentialRespondent(targetUrl, new PutMethod(), redirect),
		new PotentialRespondent(targetUrl, new DeleteMethod(), redirect))) {
	    server.start();
	    Requests requests = new HttpRequests();
	    String toHit = baseUrl + targetUrl;
	    assertTrue(requests.getResponse(toHit).code() == code);
	    String body = "body";
	    Response postResponse = requests.postResponse(toHit, body.getBytes());
	    assertTrue(postResponse.code() == code);
	    assertTrue(postResponse.body().stringValue().equals(message));
	    Response putResponse = requests.putResponse(toHit, body.getBytes());
	    assertTrue(putResponse.code() == code);
	    assertTrue(putResponse.body().stringValue().equals(message));
	    assertTrue(requests.deleteResponse(toHit).code() == code);
	}
    }

    private int availablePort() throws Exception {
	try (ServerSocket s = new ServerSocket(0)) {
	    return s.getLocalPort();
	}
    }

}
