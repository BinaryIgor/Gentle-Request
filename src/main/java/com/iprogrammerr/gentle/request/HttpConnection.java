package com.iprogrammerr.gentle.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.gentle.request.binary.Binary;
import com.iprogrammerr.gentle.request.binary.OnePacketBinary;
import com.iprogrammerr.gentle.request.binary.PacketsBinary;

public final class HttpConnection implements Connection {

	private static final String HEAD = "HEAD";
	private static final String CONTENT_LENGTH = "Content-Length";
	private final int readTimeout;
	private final int connectTimeout;
	private final boolean followRedirects;

	public HttpConnection(int readTimeout, int connectTimeout, boolean followRedirects) {
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
		this.followRedirects = followRedirects;
	}

	public HttpConnection(int readTimeout, int connectTimeout) {
		this(readTimeout, connectTimeout, false);
	}

	public HttpConnection(int timeout, boolean followRedirects) {
		this((int) (timeout * 0.75), timeout, followRedirects);
	}

	public HttpConnection(int timeout) {
		this(timeout, false);
	}

	public HttpConnection(boolean followRedirects) {
		this(5000, followRedirects);
	}

	public HttpConnection() {
		this(5000, false);
	}

	@Override
	public Response response(Request request) throws Exception {
		HttpURLConnection connection = connection(request.url(), request.method(),
				request.body().length > 0 ? true : false, request.headers());
		try {
			connection.connect();
			if (request.body().length > 0) {
				BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
				os.write(request.body());
				os.close();
			}
			int code = connection.getResponseCode();
			List<HttpHeader> responseHeaders = new ArrayList<>();
			int bodySize = 0;
			for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
				if (entry.getKey() == null) {
					continue;
				}
				for (String value : entry.getValue()) {
					HttpHeader header = new HttpHeader(entry.getKey(), value);
					responseHeaders.add(header);
					if (header.is(CONTENT_LENGTH)) {
						bodySize = bodySize(value);
					}
				}
			}
			InputStream stream = code < 400 ? connection.getInputStream()
					: connection.getErrorStream();
			byte[] responseBody = canHaveBody(code, request.method()) ? body(stream, bodySize)
					: new byte[0];
			if (bodySize > 0 && bodySize != responseBody.length) {
				throw new Exception(String.format(
						"Server sent inconsistent body, %d expected," + " but %d received",
						bodySize, responseBody.length));
			}
			return new HttpResponse(code, responseHeaders, responseBody);
		} finally {
			connection.disconnect();

		}
	}

	private boolean canHaveBody(int code, String method) {
		return !method.equalsIgnoreCase(HEAD) && code >= 200
				&& code != HttpURLConnection.HTTP_NO_CONTENT
				&& code != HttpURLConnection.HTTP_NOT_MODIFIED;
	}

	private HttpURLConnection connection(String url, String method, boolean output,
			List<Header> headers) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod(method.toUpperCase());
		connection.setDoOutput(output);
		connection.setReadTimeout(this.readTimeout);
		connection.setConnectTimeout(this.connectTimeout);
		HttpURLConnection.setFollowRedirects(this.followRedirects);
		for (Header h : headers) {
			connection.setRequestProperty(h.key(), h.value());
		}
		return connection;
	}

	private int bodySize(String value) {
		int bodySize;
		try {
			bodySize = Integer.parseInt(value.trim());
		} catch (Exception e) {
			bodySize = 0;
		}
		return bodySize;
	}

	private byte[] body(InputStream inputStream, int size) {
		try (BufferedInputStream is = new BufferedInputStream(inputStream)) {
			byte[] body;
			Binary binary = new OnePacketBinary(is);
			if (size < 1) {
				body = binary.content();
			} else {
				body = new PacketsBinary(binary, size).content();
			}
			return body;
		} catch (Exception e) {
			return new byte[0];
		}
	}
}
