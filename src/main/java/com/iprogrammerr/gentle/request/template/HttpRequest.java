package com.iprogrammerr.gentle.request.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.Request;
import com.iprogrammerr.gentle.request.initialization.ArrayToList;
import com.iprogrammerr.gentle.request.initialization.FileContent;
import com.iprogrammerr.gentle.request.initialization.Initialization;
import com.iprogrammerr.gentle.request.initialization.StickyInitialization;
import com.iprogrammerr.gentle.request.initialization.ToConcatenateList;
import com.iprogrammerr.gentle.request.initialization.UnreliableInitialization;
import com.iprogrammerr.gentle.request.initialization.UnreliableStickyInitialization;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public class HttpRequest implements Request {

	private final String url;
	private final String method;
	private final Initialization<List<Header>> headers;
	private final UnreliableInitialization<byte[]> body;

	private HttpRequest(String url, String method, Initialization<List<Header>> headers,
			UnreliableInitialization<byte[]> body) {
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}

	public HttpRequest(String url, String method, List<Header> headers,
			UnreliableInitialization<byte[]> body) {
		this(url, method, () -> headers, body);
	}

	public HttpRequest(String url, String method, UnreliableInitialization<byte[]> body,
			Header... headers) {
		this(url, method, new StickyInitialization<>(() -> new ArrayList<>(Arrays.asList(headers))),
				body);
	}

	public HttpRequest(String url, String method, byte[] body, List<Header> headers) {
		this(url, method, headers, () -> body);
	}

	public HttpRequest(String url, String method, byte[] body, Header... headers) {
		this(url, method, () -> body, headers);
	}

	public HttpRequest(String url, String method, String body, List<Header> headers) {
		this(url, method, body.getBytes(), headers);
	}

	public HttpRequest(String url, String method, String body, Header... headers) {
		this(url, method, body.getBytes(), headers);
	}

	public HttpRequest(String url, String method, List<Header> headers) {
		this(url, method, headers, () -> new byte[0]);
	}

	public HttpRequest(String url, String method, Header... headers) {
		this(url, method, () -> new byte[0], headers);
	}

	public HttpRequest(String url, String method, JSONObject body, List<Header> headers) {
		this(url, method, new StickyInitialization<>(() -> {
			headers.add(new JsonContentTypeHeader());
			return headers;
		}), new UnreliableStickyInitialization<>(() -> {
			return body.toString().getBytes();
		}));
	}

	public HttpRequest(String url, String method, JSONObject body, Header... headers) {
		this(url, method,
				new StickyInitialization<>(
						new ArrayToList<Header>(headers, new JsonContentTypeHeader())),
				new UnreliableStickyInitialization<>(() -> {
					return body.toString().getBytes();
				}));
	}

	public HttpRequest(String url, String method, Multipart body, List<Header> headers) {
		this(url, method,
				new StickyInitialization<>(new ToConcatenateList<>(headers, body.header())),
				new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public HttpRequest(String url, String method, Multipart body, Header... headers) {
		this(url, method, new StickyInitialization<>(new ArrayToList<>(headers, body.header())),
				new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public HttpRequest(String url, String method, MultipartForm body, List<Header> headers) {
		this(url, method,
				new StickyInitialization<>(new ToConcatenateList<>(headers, body.header())),
				new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public HttpRequest(String url, String method, MultipartForm body, Header... headers) {
		this(url, method, new StickyInitialization<>(new ArrayToList<>(headers, body.header())),
				new UnreliableStickyInitialization<>(() -> body.body()));
	}

	public HttpRequest(String url, String method, File body, String type, List<Header> headers) {
		this(url, method,
				new StickyInitialization<>(
						new ToConcatenateList<>(headers, new ContentTypeHeader(type))),
				new UnreliableStickyInitialization<>(new FileContent(body)));
	}

	public HttpRequest(String url, String method, File body, String type, Header... headers) {
		this(url, method,
				new StickyInitialization<>(new ArrayToList<>(headers, new ContentTypeHeader(type))),
				new UnreliableStickyInitialization<>(new FileContent(body)));
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
