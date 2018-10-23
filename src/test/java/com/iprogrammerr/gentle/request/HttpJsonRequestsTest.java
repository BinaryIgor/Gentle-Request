package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedRequests;

public final class HttpJsonRequestsTest {

    @Test
    public void canSendJson() throws Exception {
	String mockedUrl = "www.mock.com";
	JsonRequests requests = new HttpJsonRequests(new MockedRequests());
	JSONObject json = new JSONObject();
	json.put("id", 1);
	json.put("name", "abc");
	Response response = requests.postResponse(mockedUrl, json);
	validateResponse(json, response);
	response = requests.putResponse(mockedUrl, json);
	validateResponse(json, response);
	response = requests.methodResponse("HEAD", mockedUrl, json);
	validateResponse(json, response);
    }

    private void validateResponse(JSONObject expected, Response response) throws Exception {
	assertTrue(response.header("Content-Type").value().equalsIgnoreCase("application/json"));
	assertTrue(expected.getInt("id") == response.body().jsonValue().getInt("id"));
	assertTrue(expected.getString("name").equals(response.body().jsonValue().getString("name")));
    }

}
