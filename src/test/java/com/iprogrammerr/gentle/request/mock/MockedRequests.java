package com.iprogrammerr.gentle.request.mock;

import java.util.Arrays;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.HttpResponse;
import com.iprogrammerr.gentle.request.Requests;
import com.iprogrammerr.gentle.request.Response;

public final class MockedRequests implements Requests {

    private final MockedResponse response;

    public MockedRequests(MockedResponse response) {
	this.response = response;
    }

    public MockedRequests() {
	this((url, body, headers) -> new HttpResponse(200, Arrays.asList(headers), body));
    }

    @Override
    public Response getResponse(String url, Header... headers) throws Exception {
	return this.response.response(url, new byte[0], headers);
    }

    @Override
    public Response postResponse(String url, byte[] body, Header... headers) throws Exception {
	return this.response.response(url, body, headers);
    }

    @Override
    public Response postResponse(String url, Header... headers) throws Exception {
	return this.response.response(url, new byte[0], headers);
    }

    @Override
    public Response putResponse(String url, byte[] body, Header... headers) throws Exception {
	return this.response.response(url, body, headers);
    }

    @Override
    public Response deleteResponse(String url, Header... headers) throws Exception {
	return this.response.response(url, new byte[0], headers);
    }

    @Override
    public Response methodResponse(String method, String url, byte[] body, Header... headers) throws Exception {
	return this.response.response(url, body, headers);
    }

    @Override
    public Response methodResponse(String method, String url, Header... headers) throws Exception {
	return this.response.response(url, new byte[0], headers);
    }

}
