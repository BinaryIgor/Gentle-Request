package com.iprogrammerr.gentle.request;

import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public interface MultipartRequests {

	Response postResponse(String url, MultipartForm multipart, HttpHeader... headers) throws Exception;

	Response postResponse(String url, Multipart multipart, HttpHeader... headers) throws Exception;

	Response putResponse(String url, MultipartForm multipart, HttpHeader... headers) throws Exception;

	Response putResponse(String url, Multipart multipart, HttpHeader... headers) throws Exception;

	Response methodResponse(String method, String url, MultipartForm multipart, HttpHeader... headers)
			throws Exception;

	Response methodResponse(String method, String url, Multipart multipart, HttpHeader... headers)
			throws Exception;
}
