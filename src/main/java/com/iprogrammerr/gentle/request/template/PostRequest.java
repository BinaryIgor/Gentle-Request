package com.iprogrammerr.gentle.request.template;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.EmptyRequest;
import com.iprogrammerr.gentle.request.FilledRequest;
import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class PostRequest extends RequestEnvelope {

	private static final String POST = "POST";

	public PostRequest(String url, List<Header> headers, String contentType, byte[] body) {
		super(new FilledRequest(POST, url, headers, contentType, body));
	}

	public PostRequest(String url, String contentType, byte[] body, Header... headers) {
		super(new FilledRequest(POST, url, contentType, body, headers));
	}

	public PostRequest(String url, List<Header> headers, byte[] body) {
		super(new FilledRequest(POST, url, headers, body));
	}

	public PostRequest(String url, byte[] body, Header... headers) {
		super(new FilledRequest(POST, url, body, headers));
	}

	public PostRequest(String url, List<Header> headers, String body) {
		super(new FilledRequest(POST, url, headers, body));
	}

	public PostRequest(String url, String body, Header... headers) {
		super(new FilledRequest(POST, url, body, headers));
	}

	public PostRequest(String url, List<Header> headers) {
		super(new EmptyRequest(POST, url, headers));
	}

	public PostRequest(String url, Header... headers) {
		super(new EmptyRequest(POST, url, headers));
	}

	public PostRequest(String url, List<Header> headers, JSONObject body) {
		super(new FilledRequest(POST, url, headers, body));
	}

	public PostRequest(String url, JSONObject body, Header... headers) {
		super(new FilledRequest(POST, url, body, headers));
	}

	public PostRequest(String url, List<Header> headers, Multipart body) {
		super(new FilledRequest(POST, url, headers, body));
	}

	public PostRequest(String url, Multipart body, Header... headers) {
		super(new FilledRequest(POST, url, body, headers));
	}

	public PostRequest(String url, List<Header> headers, MultipartForm body) {
		super(new FilledRequest(POST, url, headers, body));
	}

	public PostRequest(String url, MultipartForm body, Header... headers) {
		super(new FilledRequest(POST, url, body, headers));
	}

	public PostRequest(String url, List<Header> headers, String contentType, File body) {
		super(new FilledRequest(POST, url, headers, contentType, body));
	}

	public PostRequest(String url, String contentType, File body, Header... headers) {
		super(new FilledRequest(POST, url, contentType, body, headers));
	}
}
