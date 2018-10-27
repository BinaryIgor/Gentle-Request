package com.iprogrammerr.gentle.request;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import com.iprogrammerr.gentle.request.binary.PacketsBinary;

public final class HttpFileRequests implements FileRequests {

	private final Requests base;

	public HttpFileRequests(Requests base) {
		this.base = base;
	}

	@Override
	public Response postResponse(String url, File file, String type, HttpHeader... headers)
			throws Exception {
		return this.base.postResponse(url, body(file), headers(headers, type));
	}

	@Override
	public Response putResponse(String url, File file, String type, HttpHeader... headers)
			throws Exception {
		return this.base.putResponse(url, body(file), headers(headers, type));
	}

	@Override
	public Response methodResponse(String method, String url, File file, String type,
			HttpHeader... headers) throws Exception {
		return this.base.methodResponse(method, url, body(file), headers(headers, type));
	}

	private byte[] body(File file) throws Exception {
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
			return new PacketsBinary(is, file.length()).content();
		}
	}

	private HttpHeader[] headers(HttpHeader[] headers, String type) {
		HttpHeader header = new HttpHeader("Content-Type", type);
		if (headers.length == 0) {
			headers = new HttpHeader[] { header };
		} else {
			headers = Arrays.copyOf(headers, headers.length + 1);
			headers[headers.length - 1] = header;
		}
		return headers;
	}
}
