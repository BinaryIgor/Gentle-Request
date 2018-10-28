package com.iprogrammerr.gentle.request.template;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.EmptyRequest;
import com.iprogrammerr.gentle.request.FilledRequest;
import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class PutRequest extends RequestEnvelope {

	private static final String PUT = "PUT";

	public PutRequest(String url, List<Header> headers, String contentType, byte[] body) {
		super(new FilledRequest(PUT, url, headers, contentType, body));
	}

	public PutRequest(String url, String contentType, byte[] body, Header... headers) {
		super(new FilledRequest(PUT, url, contentType, body, headers));
	}

	public PutRequest(String url, List<Header> headers, byte[] body) {
		super(new FilledRequest(PUT, url, headers, body));
	}

	public PutRequest(String url, byte[] body, Header... headers) {
		super(new FilledRequest(PUT, url, body, headers));
	}

	public PutRequest(String url, List<Header> headers, String body) {
		super(new FilledRequest(PUT, url, headers, body));
	}

	public PutRequest(String url, String body, Header... headers) {
		super(new FilledRequest(PUT, url, body, headers));
	}

	public PutRequest(String url, List<Header> headers) {
		super(new EmptyRequest(PUT, url, headers));
	}

	public PutRequest(String url, Header... headers) {
		super(new EmptyRequest(PUT, url, headers));
	}

	public PutRequest(String url, List<Header> headers, JSONObject body) {
		super(new FilledRequest(PUT, url, headers, body));
	}

	public PutRequest(String url, JSONObject body, Header... headers) {
		super(new FilledRequest(PUT, url, body, headers));
	}

	public PutRequest(String url, List<Header> headers, Multipart body) {
		super(new FilledRequest(PUT, url, headers, body));
	}

	public PutRequest(String url, Multipart body, Header... headers) {
		super(new FilledRequest(PUT, url, body, headers));
	}

	public PutRequest(String url, List<Header> headers, MultipartForm body) {
		super(new FilledRequest(PUT, url, headers, body));
	}

	public PutRequest(String url, MultipartForm body, Header... headers) {
		super(new FilledRequest(PUT, url, body, headers));
	}

	public PutRequest(String url, List<Header> headers, String contentType, File body) {
		super(new FilledRequest(PUT, url, headers, contentType, body));
	}

	public PutRequest(String url, String contentType, File body, Header... headers) {
		super(new FilledRequest(PUT, url, contentType, body, headers));
	}
}
