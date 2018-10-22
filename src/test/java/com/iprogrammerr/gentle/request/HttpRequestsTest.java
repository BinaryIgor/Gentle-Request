package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.json.JSONObject;
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
	assertTrue(toCatch.hasCatched(() -> this.requests.getResponse(badProtocolUrl)));
	assertTrue(toCatch.hasCatched(() -> this.requests.postResponse(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> this.requests.putResponse(badProtocolUrl, new byte[0])));
	assertTrue(toCatch.hasCatched(() -> this.requests.deleteResponse(badProtocolUrl)));
    }

    @Test
    public void canRequest() throws Exception {
	Consumer<Response> consumer = new Consumer<Response>() {

	    @Override
	    public void accept(Response response) {
		assertTrue(response.hasProperCode());
		assertTrue(response.body().value().length > 0);
	    }

	};
	consumer.accept(this.requests.getResponse(BASE_URL));
	JSONObject json = new JSONObject();
	json.put("id", 44);
	json.put("name", "super");
	byte[] body = json.toString().getBytes();
	consumer.accept(this.requests.postResponse(BASE_URL, body, new Header("Authorization", "Secret")));
	consumer.accept(this.requests.putResponse(BASE_URL + "/1", body));
	consumer.accept(this.requests.deleteResponse(BASE_URL + "/1"));
    }

}
