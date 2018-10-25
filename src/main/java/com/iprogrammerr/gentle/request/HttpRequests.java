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

public final class HttpRequests implements Requests {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String CONTENT_LENGTH = "Content-Length";
    private final int readTimeout;
    private final int connectTimeout;

    public HttpRequests(int readTimeout, int connectTimeout) {
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
    public Response getResponse(String url, Header... headers) throws Exception {
	return response(url, GET, new byte[0], headers);
    }

    @Override
    public Response postResponse(String url, byte[] body, Header... headers) throws Exception {
	return response(url, POST, body, headers);
    }

    @Override
    public Response postResponse(String url, Header... headers) throws Exception {
	return response(url, POST, new byte[0], headers);
    }

    @Override
    public Response putResponse(String url, byte[] body, Header... headers) throws Exception {
	return response(url, PUT, body, headers);
    }

    @Override
    public Response deleteResponse(String url, Header... headers) throws Exception {
	return response(url, DELETE, new byte[0], headers);
    }

    @Override
    public Response methodResponse(String method, String url, byte[] body, Header... headers) throws Exception {
	return response(url, method, body, headers);
    }

    @Override
    public Response methodResponse(String method, String url, Header... headers) throws Exception {
	return response(url, method, new byte[0], headers);
    }

    private Response response(String url, String method, byte[] body, Header... headers) throws Exception {
	HttpURLConnection connection = connection(url, method, body.length > 0 ? true : false, headers);
	try {
	    connection.connect();
	    if (body.length > 0) {
		BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
		os.write(body);
		os.close();
	    }
	    int code = connection.getResponseCode();
	    List<Header> responseHeaders = new ArrayList<>();
	    int bodySize = 0;
	    for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
		if (entry.getKey() == null) {
		    continue;
		}
		for (String value : entry.getValue()) {
		    Header header = new Header(entry.getKey(), value);
		    responseHeaders.add(header);
		    if (header.is(CONTENT_LENGTH)) {
			bodySize = bodySize(value);
		    }
		}
	    }
	    byte[] responseBody = body(code < 400 ? connection.getInputStream() : connection.getErrorStream(),
		    bodySize);
	    return new HttpResponse(code, responseHeaders, responseBody);
	} finally {
	    connection.disconnect();
	}
    }

    private HttpURLConnection connection(String url, String method, boolean output, Header... headers)
	    throws Exception {
	HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	connection.setRequestMethod(method);
	connection.setDoOutput(output);
	connection.setReadTimeout(this.readTimeout);
	connection.setConnectTimeout(this.connectTimeout);
	connection.setFollowRedirects(false);
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
	try (BufferedInputStream is = new BufferedInputStream(inputStream)) {
	    byte[] body;
	    Binary binary = new OnePacketBinary(is);
	    if (size < 1) {
		body = binary.content();
	    } else {
		body = new PacketsBinary(binary, size).content();
	    }
	    return body;
	}
    }

}
