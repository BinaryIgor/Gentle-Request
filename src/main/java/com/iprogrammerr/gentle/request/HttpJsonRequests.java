package com.iprogrammerr.gentle.request;

import java.util.Arrays;

import org.json.JSONObject;

public final class HttpJsonRequests implements JsonRequests {

    private final Requests base;

    public HttpJsonRequests(Requests base) {
	this.base = base;
    }

    @Override
    public Response getResponse(String url, Header... headers) throws Exception {
	return this.base.getResponse(url, headers);
    }

    @Override
    public Response postResponse(String url, JSONObject json, Header... headers) throws Exception {
	return this.base.postResponse(url, json.toString().getBytes(), headers(headers));
    }

    @Override
    public Response postResponse(String url, Header... headers) throws Exception {
	return this.base.postResponse(url, headers);
    }

    @Override
    public Response putResponse(String url, JSONObject json, Header... headers) throws Exception {
	return this.base.putResponse(url, json.toString().getBytes(), headers(headers));
    }

    @Override
    public Response deleteResponse(String url, Header... headers) throws Exception {
	return this.base.deleteResponse(url, headers);
    }

    @Override
    public Response methodResponse(String method, String url, JSONObject json, Header... headers) throws Exception {
	return this.base.methodResponse(method, url, json.toString().getBytes(), headers(headers));
    }

    @Override
    public Response methodResponse(String method, String url, Header... headers) throws Exception {
	return this.base.methodResponse(method, url, headers);
    }

    private Header[] headers(Header[] headers) {
	Header header = new Header("Content-Type", "application/json");
	if (headers.length == 0) {
	    headers = new Header[] { header };
	} else {
	    headers = Arrays.copyOf(headers, headers.length + 1);
	    headers[headers.length - 1] = header;
	}
	return headers;
    }

}
