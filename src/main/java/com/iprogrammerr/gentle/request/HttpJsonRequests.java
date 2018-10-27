package com.iprogrammerr.gentle.request;

import java.util.Arrays;

import org.json.JSONObject;

public final class HttpJsonRequests implements JsonRequests {

	private static final HttpHeader JSON_HEADER = new HttpHeader("Content-Type", "application/json");
	private final Requests base;

	public HttpJsonRequests(Requests base) {
		this.base = base;
	}

	@Override
	public Response getResponse(String url, HttpHeader... headers) throws Exception {
		return this.base.getResponse(url, headers);
	}

	@Override
	public Response postResponse(String url, JSONObject json, HttpHeader... headers) throws Exception {
		return this.base.postResponse(url, json.toString().getBytes(), headers(headers));
	}

	@Override
	public Response postResponse(String url, HttpHeader... headers) throws Exception {
		return this.base.postResponse(url, headers);
	}

	@Override
	public Response putResponse(String url, JSONObject json, HttpHeader... headers) throws Exception {
		return this.base.putResponse(url, json.toString().getBytes(), headers(headers));
	}

	@Override
	public Response deleteResponse(String url, HttpHeader... headers) throws Exception {
		return this.base.deleteResponse(url, headers);
	}

	@Override
	public Response methodResponse(String method, String url, JSONObject json, HttpHeader... headers)
			throws Exception {
		return this.base.methodResponse(method, url, json.toString().getBytes(), headers(headers));
	}

	@Override
	public Response methodResponse(String method, String url, HttpHeader... headers) throws Exception {
		return this.base.methodResponse(method, url, headers);
	}

	private HttpHeader[] headers(HttpHeader[] headers) {
		if (headers.length == 0) {
			headers = new HttpHeader[] { JSON_HEADER };
		} else {
			headers = Arrays.copyOf(headers, headers.length + 1);
			headers[headers.length - 1] = JSON_HEADER;
		}
		return headers;
	}

}
