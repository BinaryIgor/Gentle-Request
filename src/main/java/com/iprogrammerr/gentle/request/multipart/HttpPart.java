package com.iprogrammerr.gentle.request.multipart;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.iprogrammerr.gentle.request.binary.BinaryWithAttributes;
import com.iprogrammerr.gentle.request.binary.BinaryWithSingleAttribute;
import com.iprogrammerr.gentle.request.initialization.UnreliableInitialization;
import com.iprogrammerr.gentle.request.initialization.UnreliableStickyInitialization;

public final class HttpPart implements Part {

	private static final String CRLF = "\r\n";
	private static final String TEXT_PLAIN = "text/plain";
	private static final String CONTENT_TYPE_PREFIX = "Content-Type: ";
	private static final String COLON = ":";
	private byte[] source;
	private final UnreliableInitialization<BinaryWithAttributes> data;

	private HttpPart(byte[] parsed, UnreliableInitialization<BinaryWithAttributes> data) {
		this.source = parsed;
		this.data = data;
	}

	public HttpPart(byte[] source) {
		this(source, new UnreliableStickyInitialization<>(() -> {
			String[] lines = new String(source).split(CRLF + CRLF);
			String contentType;
			int bodyIndex = (CRLF + CRLF).getBytes().length;
			if (!lines[0].isEmpty()) {
				bodyIndex += lines[0].getBytes().length;
				contentType = lines[0].split(COLON)[1].trim();
			} else {
				contentType = TEXT_PLAIN;
			}
			return new BinaryWithSingleAttribute(Arrays.copyOfRange(source, bodyIndex, source.length), "contentType",
					contentType);
		}));
	}

	public HttpPart(String contentType, byte[] content) {
		this(new byte[0], new UnreliableStickyInitialization<>(
				() -> new BinaryWithSingleAttribute(content, "contentType", contentType)));
	}

	public HttpPart(String content) {
		this(TEXT_PLAIN, content.getBytes());
	}

	@Override
	public String contentType() throws Exception {
		return this.data.value().attribute("contentType");
	}

	@Override
	public byte[] content() throws Exception {
		return this.data.value().content();
	}

	@Override
	public byte[] source() throws Exception {
		if (this.source.length < 1) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(content().length);
			baos.write(CRLF.getBytes());
			if (!contentType().equals(TEXT_PLAIN)) {
				baos.write(CONTENT_TYPE_PREFIX.getBytes());
				baos.write(contentType().getBytes());
				baos.write(CRLF.getBytes());
			}
			baos.write(CRLF.getBytes());
			baos.write(content());
			this.source = baos.toByteArray();
		}
		return this.source;
	}
}
