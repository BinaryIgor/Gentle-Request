package com.iprogrammerr.gentle.request;

public interface Requests {

	Response getResponse(String url, HttpHeader... headers) throws Exception;

	Response postResponse(String url, byte[] body, HttpHeader... headers) throws Exception;

	Response postResponse(String url, HttpHeader... headers) throws Exception;

	Response putResponse(String url, byte[] body, HttpHeader... headers) throws Exception;

	Response deleteResponse(String url, HttpHeader... headers) throws Exception;

	Response methodResponse(String method, String url, byte[] body, HttpHeader... headers)
			throws Exception;

	Response methodResponse(String method, String url, HttpHeader... headers) throws Exception;
}
