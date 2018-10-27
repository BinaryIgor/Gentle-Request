package com.iprogrammerr.gentle.request;

import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class HttpMultipartRequests implements MultipartRequests {

	private final Requests base;

	public HttpMultipartRequests(Requests base) {
		this.base = base;
	}

	@Override
	public Response postResponse(String url, MultipartForm multipart, HttpHeader... headers)
			throws Exception {
		return this.base.postResponse(url, multipart.body(), multipart.header());
	}

	@Override
	public Response postResponse(String url, Multipart multipart, HttpHeader... headers)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response putResponse(String url, MultipartForm multipart, HttpHeader... headers)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response putResponse(String url, Multipart multipart, HttpHeader... headers)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response methodResponse(String method, String url, MultipartForm multipart,
			HttpHeader... headers) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response methodResponse(String method, String url, Multipart multipart,
			HttpHeader... headers) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private HttpHeader[] headers(HttpHeader[] headers) {

		return headers;
	}

}
