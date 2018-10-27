package com.iprogrammerr.gentle.request.multipart;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.iprogrammerr.gentle.request.data.Attributes;
import com.iprogrammerr.gentle.request.data.TypedMap;

public final class HttpPart implements Part {

	private static final String CRLF = "\r\n";
	private static final String TEXT_PLAIN = "text/plain";
	private static final String CONTENT_TYPE_PREFIX = "Content-Type: ";
	private static final String COLON = ":";
	private byte[] parsed;
	private final TypedMap data;

	private HttpPart(byte[] parsed, TypedMap data) {
		this.parsed = parsed;
		this.data = data;
	}

	public HttpPart(byte[] parsed) {
		this(parsed, new Attributes());
	}

	public HttpPart(String contentType, byte[] content) {
		this(new byte[0], new Attributes().put("contentType", contentType).put("content", content));
	}

	public HttpPart(String content) {
		this(TEXT_PLAIN, content.getBytes());
	}

	@Override
	public String contentType() throws Exception {
		if (this.data.isEmpty()) {
			read();
		}
		return this.data.stringValue("contentType");
	}

	@Override
	public byte[] content() throws Exception {
		if (this.data.isEmpty()) {
			read();
		}
		return this.data.binaryValue("content");
	}

	@Override
	public byte[] parsed() throws Exception {
		if (this.parsed.length < 1) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(content().length);
			baos.write(CRLF.getBytes());
			if (!contentType().equals(TEXT_PLAIN)) {
				baos.write(CONTENT_TYPE_PREFIX.getBytes());
				baos.write(contentType().getBytes());
				baos.write(CRLF.getBytes());
			}
			baos.write(CRLF.getBytes());
			baos.write(content());
			this.parsed = baos.toByteArray();
		}
		return this.parsed;
	}

	private void read() throws Exception {
		String[] lines = new String(this.parsed).split(CRLF + CRLF);
		String contentType;
		int bodyIndex = (CRLF + CRLF).getBytes().length;
		if (!lines[0].isEmpty()) {
			bodyIndex += lines[0].getBytes().length;
			contentType = lines[0].split(COLON)[1].trim();
		} else {
			contentType = TEXT_PLAIN;
		}
		this.data.put("contentType", contentType).put("content",
				Arrays.copyOfRange(this.parsed, bodyIndex, this.parsed.length));
	}

}
