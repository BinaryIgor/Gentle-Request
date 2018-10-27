package com.iprogrammerr.gentle.request.template;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class PostRequest extends RequestEnvelope {

	private static final String POST = "POST";

	public PostRequest(String url, byte[] body, List<Header> headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, byte[] body, Header... headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, String body, List<Header> headers) {
		this(url, body.getBytes(), headers);
	}

	public PostRequest(String url, String body, Header... headers) {
		this(url, body.getBytes(), headers);
	}

	public PostRequest(String url, List<Header> headers) {
		super(new HttpRequest(url, POST, headers));
	}

	public PostRequest(String url, Header... headers) {
		super(new HttpRequest(url, POST, headers));
	}

	public PostRequest(String url, JSONObject body, List<Header> headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, JSONObject body, Header... headers) {
		super(new HttpRequest(url, POST, headers));
	}

	public PostRequest(String url, Multipart body, List<Header> headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, Multipart body, Header... headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, MultipartForm body, List<Header> headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, MultipartForm body, Header... headers) {
		super(new HttpRequest(url, POST, body, headers));
	}

	public PostRequest(String url, File body, String type, List<Header> headers) {
		super(new HttpRequest(url, POST, body, type, headers));
	}

	public PostRequest(String url, File body, String type, Header... headers) {
		super(new HttpRequest(url, POST, body, type, headers));
	}

}
