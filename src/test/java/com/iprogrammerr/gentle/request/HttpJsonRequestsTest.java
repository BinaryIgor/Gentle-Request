package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedRequests;

public final class HttpJsonRequestsTest {

	@Test
	public void canSendAndReceive() throws Exception {
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("name", "abc");
		json.put("scale", 34.5);
		JsonRequests requests = new HttpJsonRequests(new MockedRequests((url, body, headers) -> {
			byte[] rb = json.toString().getBytes();
			List<HttpHeader> hr = new ArrayList<>();
			hr.add(new HttpHeader("Content-Type", "application/json"));
			hr.add(new HttpHeader("Content-Length", String.valueOf(rb.length)));
			for (HttpHeader h : headers) {
				hr.add(h);
			}
			return new HttpResponse(200, hr, json.toString().getBytes());
		}));
		assertThat(requests, new JsonRequestsThatAreSendingAndResponding(json));
	}
}
