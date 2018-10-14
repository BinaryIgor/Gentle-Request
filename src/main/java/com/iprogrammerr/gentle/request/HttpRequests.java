package com.iprogrammerr.gentle.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpRequests implements Requests {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String CONTENT_LENGTH = "Content-Length";
    private final int readTimeout;
    private final int connectTimeout;

    private HttpRequests(int readTimeout, int connectTimeout) {
	this.readTimeout = readTimeout;
	this.connectTimeout = connectTimeout;
    }

    public HttpRequests(int timeout) {
	this((int) (timeout * 0.75), timeout);
    }

    public HttpRequests() {
	this(5000);
    }

    @Override
    public Response get(String url, Header... headers) throws Exception {
	return response(url, GET, new byte[0], headers);
    }

    @Override
    public Response post(String url, byte[] body, Header... headers) throws Exception {
	return response(url, POST, body, headers);
    }

    @Override
    public Response put(String url, byte[] body, Header... headers) throws Exception {
	return response(url, PUT, body, headers);
    }

    @Override
    public Response delete(String url, Header... headers) throws Exception {
	return response(url, DELETE, new byte[0], headers);
    }

    private Response response(String url, String method, byte[] body, Header... headers) throws Exception {
	HttpURLConnection connection = connection(url, method, headers);
	connection.connect();
	if (body.length > 0) {
	    BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
	    os.write(body);
	}
	int code = connection.getResponseCode();
	List<Header> responseHeaders = new ArrayList<>();
	int bodySize = 0;
	for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
	    for (String value : entry.getValue()) {
		Header header = new Header(entry.getKey(), value);
		responseHeaders.add(header);
		if (header.is(CONTENT_LENGTH)) {
		    bodySize = bodySize(value);
		}
	    }
	}
	byte[] responseBody = body(connection.getInputStream(), bodySize);
	connection.disconnect();
	return new Response(code, responseHeaders, responseBody);
    }

    private HttpURLConnection connection(String url, String method, Header... headers) throws Exception {
	HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	connection.setRequestMethod(method);
	if (POST.equals(method) || PUT.equals(method)) {
	    connection.setDoOutput(true);
	}
	connection.setReadTimeout(this.readTimeout);
	connection.setConnectTimeout(this.connectTimeout);
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

    private byte[] body(InputStream inputStream, int size) throws Exception {
	BufferedInputStream bis = new BufferedInputStream(inputStream);
	byte[] body;
	if (size < 1) {
	    body = packet(bis);
	} else {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    int read = 0;
	    while (read != size) {
		byte[] packet = packet(bis);
		baos.write(packet);
		read += packet.length;
	    }
	    body = baos.toByteArray();
	}
	return body;
    }

    private byte[] packet(InputStream inputStream) throws Exception {
	int available = inputStream.available();
	if (available == 0) {
	    available = 1024;
	}
	byte[] buffer = new byte[available];
	int read = inputStream.read(buffer);
	byte[] readBytes;
	if (read == buffer.length) {
	    readBytes = buffer;
	} else if (read > 0) {
	    readBytes = new byte[read];
	    for (int i = 0; i < read; i++) {
		readBytes[i] = buffer[i];
	    }
	} else {
	    readBytes = new byte[0];
	}
	return readBytes;
    }

}
