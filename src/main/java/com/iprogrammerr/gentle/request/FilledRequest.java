package com.iprogrammerr.gentle.request;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.initialization.FileContent;
import com.iprogrammerr.gentle.request.initialization.Initialization;
import com.iprogrammerr.gentle.request.initialization.StickyInitialization;
import com.iprogrammerr.gentle.request.initialization.UnreliableInitialization;
import com.iprogrammerr.gentle.request.initialization.UnreliableStickyInitialization;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;
import com.iprogrammerr.gentle.request.template.ContentLengthHeader;
import com.iprogrammerr.gentle.request.template.ContentTypeHeader;

public final class FilledRequest implements Request {

	private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
	private static final String TEXT_PLAIN = "text/plain";
	private static final String JSON = "application/json";
	private final String method;
	private final String url;
	private final Initialization<List<Header>> headers;
	private final UnreliableInitialization<byte[]> body;

	private FilledRequest(String method, String url, Initialization<List<Header>> headers,
			UnreliableInitialization<byte[]> body) {
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.body = body;
	}

	private FilledRequest(String method, String url, List<Header> headers, Header contentType,
			UnreliableInitialization<byte[]> body) {
		this(method, url, new StickyInitialization<>(() -> {
			headers.add(contentType);
			try {
				headers.add(new ContentLengthHeader(body.value().length));
			} catch (Exception e) {
				headers.add(new ContentLengthHeader(0));
			}
			return headers;
		}), body);
	}

	private FilledRequest(String method, String url, List<Header> headers, String contentType,
			UnreliableInitialization<byte[]> body) {
		this(method, url, headers, new ContentTypeHeader(contentType), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, String contentType, byte[] body) {
		this(method, url, headers, contentType, () -> body);
	}

	public FilledRequest(String method, String url, String contentType, byte[] body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), contentType, body);
	}

	public FilledRequest(String method, String url, List<Header> headers, byte[] body) {
		this(method, url, headers, FORM_URL_ENCODED, () -> body);
	}

	public FilledRequest(String method, String url, byte[] body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, String body) {
		this(method, url, headers, new ContentTypeHeader(TEXT_PLAIN),
				new UnreliableStickyInitialization<>(() -> body.getBytes()));
	}

	public FilledRequest(String method, String url, String body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, JSONObject body) {
		this(method, url, headers, new ContentTypeHeader(JSON),
				new UnreliableStickyInitialization<>(() -> body.toString().getBytes()));
	}

	public FilledRequest(String method, String url, JSONObject body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, Multipart body) {
		this(method, url, headers, body.header(), new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public FilledRequest(String method, String url, Multipart body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, MultipartForm body) {
		this(method, url, headers, body.header(), new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public FilledRequest(String method, String url, MultipartForm body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), body);
	}

	public FilledRequest(String method, String url, List<Header> headers, String contentType, File body) {
		this(method, url, headers, new ContentTypeHeader(contentType),
				new UnreliableStickyInitialization<>(new FileContent(body)));
	}

	public FilledRequest(String method, String url, String contentType, File body, Header... headers) {
		this(method, url, new ArrayList<>(Arrays.asList(headers)), contentType, body);
	}

	@Override
	public String url() {
		return this.url;
	}

	@Override
	public String method() {
		return this.method;
	}

	@Override
	public List<Header> headers() {
		return this.headers.value();
	}

	@Override
	public byte[] body() throws Exception {
		return this.body.value();
	}

	@Override
	public void addHeader(Header header) {
		this.headers.value().add(header);
	}
}
