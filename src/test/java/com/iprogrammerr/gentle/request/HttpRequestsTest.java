package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.Test;

import com.iprogrammerr.gentle.request.exception.ToCatchException;

public final class HttpRequestsTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private final HttpRequests requests;

    public HttpRequestsTest() {
	this.requests = new HttpRequests();
    }

    @Test
    public void shouldNotAcceptBadProtocols() {
	ToCatchException toCatch = new ToCatchException();
	String badProtocolUrl = BASE_URL.replace("http", "abc");
	assertTrue(toCatch.hasCatched(() -> this.requests.get(badProtocolUrl)));
	assertTrue(toCatch.hasCatched(() -> this.requests.post(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> this.requests.put(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> this.requests.delete(badProtocolUrl)));
    }

    @Test
    public void canRequest() throws Exception {
	Consumer<Response> consumer = new Consumer<Response>() {

	    @Override
	    public void accept(Response response) {
		assertTrue(response.hasProperCode());
		assertTrue(response.body().length > 0);
	    }

	};
	consumer.accept(this.requests.get(BASE_URL));
	byte[] body = "{\"value\": 44}".getBytes();
	consumer.accept(this.requests.post(BASE_URL, body));
	consumer.accept(this.requests.put(BASE_URL + "/1", body));
	consumer.accept(this.requests.delete(BASE_URL + "/1"));
    }

}
