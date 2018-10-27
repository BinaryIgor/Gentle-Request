package com.iprogrammerr.gentle.request;

import org.json.JSONObject;

public interface JsonRequests {

	Response getResponse(String url, HttpHeader... headers) throws Exception;

	Response postResponse(String url, JSONObject json, HttpHeader... headers) throws Exception;

	Response postResponse(String url, HttpHeader... headers) throws Exception;

	Response putResponse(String url, JSONObject json, HttpHeader... headers) throws Exception;

	Response deleteResponse(String url, HttpHeader... headers) throws Exception;

	Response methodResponse(String method, String url, JSONObject json, HttpHeader... headers)
			throws Exception;

	Response methodResponse(String method, String url, HttpHeader... headers) throws Exception;
}
