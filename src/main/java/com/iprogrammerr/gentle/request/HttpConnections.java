package com.iprogrammerr.gentle.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.gentle.request.binary.Binary;
import com.iprogrammerr.gentle.request.binary.OnePacketBinary;
import com.iprogrammerr.gentle.request.binary.PacketsBinary;

public final class HttpConnections implements Connections {

	private static final int OK = 200;
	private static final int NO_CONTENT = 204;
	private static final int NOT_MODIFIED = 304;
	private static final int BAD_REQUEST = 400;
	private final int readTimeout;
	private final int connectTimeout;
	private final boolean redirects;

	public HttpConnections(int readTimeout, int connectTimeout, boolean redirects) {
		this.readTimeout = readTimeout;
		this.connectTimeout = connectTimeout;
		this.redirects = redirects;
	}

	public HttpConnections(int readTimeout, int connectTimeout) {
		this(readTimeout, connectTimeout, false);
	}

	public HttpConnections(int timeout, boolean redirects) {
		this((int) (timeout * 0.75), timeout, redirects);
	}

	public HttpConnections(int timeout) {
		this(timeout, false);
	}

	public HttpConnections(boolean followRedirects) {
		this(5000, followRedirects);
	}

	public HttpConnections() {
		this(5000, false);
	}

	@Override
	public Response response(Request request) throws Exception {
		HttpURLConnection connection = connection(request);
		try {
			connection.connect();
			if (request.body().length > 0) {
				writeBody(connection.getOutputStream(), request.body());
			}
			int code = connection.getResponseCode();
			List<Header> responseHeaders = headers(connection);
			int bodySize = bodySize(responseHeaders);
			InputStream stream = code < BAD_REQUEST ? connection.getInputStream() : connection.getErrorStream();
			byte[] responseBody = canHaveBody(code, request.method()) ? body(stream, bodySize) : new byte[0];
			if (bodySize > 0 && bodySize != responseBody.length) {
				throw new Exception(String.format("Server sent inconsistent body, %d expected, but %d received",
						bodySize, responseBody.length));
			}
			return new HttpResponse(code, responseHeaders, responseBody);
		} finally {
			connection.disconnect();
		}
	}

	private void writeBody(OutputStream output, byte[] body) throws Exception {
		BufferedOutputStream os = new BufferedOutputStream(output);
		os.write(body);
		os.close();
	}

	private List<Header> headers(URLConnection connection) {
		List<Header> headers = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
			// Yes, httpUrlConnection can have null keys in headers map...
			if (entry.getKey() == null) {
				continue;
			}
			for (String value : entry.getValue()) {
				headers.add(new HttpHeader(entry.getKey(), value));
			}
		}
		return headers;
	}

	private boolean canHaveBody(int code, String method) {
		return !method.equalsIgnoreCase("HEAD") && code >= OK && code != NO_CONTENT && code != NOT_MODIFIED;
	}

	private HttpURLConnection connection(Request request) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(request.url()).openConnection();
		connection.setRequestMethod(request.method().toUpperCase());
		connection.setDoOutput(request.body().length > 0 ? true : false);
		connection.setReadTimeout(this.readTimeout);
		connection.setConnectTimeout(this.connectTimeout);
		HttpURLConnection.setFollowRedirects(this.redirects);
		for (Header h : request.headers()) {
			connection.setRequestProperty(h.key(), h.value());
		}
		return connection;
	}

	private int bodySize(List<Header> headers) {
		int size = 0;
		try {
			for (Header h : headers) {
				if (h.is("Content-Length")) {
					size = Integer.parseInt(h.value().trim());
					break;
				}
			}
		} catch (Exception e) {
			size = 0;
		}
		return size;
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
