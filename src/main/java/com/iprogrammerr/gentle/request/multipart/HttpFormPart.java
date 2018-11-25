package com.iprogrammerr.gentle.request.multipart;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.iprogrammerr.bright.server.initialization.UnreliableInitialization;
import com.iprogrammerr.bright.server.initialization.UnreliableStickyInitialization;
import com.iprogrammerr.gentle.request.binary.BinaryPattern;
import com.iprogrammerr.gentle.request.binary.BinaryWithAttributes;
import com.iprogrammerr.gentle.request.binary.HeadBodyPattern;
import com.iprogrammerr.gentle.request.binary.DefaultBinaryWithAttributes;

public final class HttpFormPart implements FormPart {

	private static final String FIRST_LINE_PREFIX = "Content-Disposition: form-data; ";
	private static final String CRLF = "\r\n";
	private static final String TEXT_PLAIN = "text/plain";
	private static final String SEMICOLON = ";";
	private static final String COLON = ":";
	private byte[] source;
	private final UnreliableInitialization<BinaryWithAttributes> data;

	private HttpFormPart(byte[] source, UnreliableInitialization<BinaryWithAttributes> data) {
		this.source = source;
		this.data = data;
	}

	public HttpFormPart(String name, String filename, String contentType, byte[] content) {
		this(new byte[0], new UnreliableStickyInitialization<>(() -> {
			Map<String, String> attributes = new HashMap<>();
			attributes.put("name", name);
			attributes.put("filename", filename);
			attributes.put("contentType", contentType);
			return new DefaultBinaryWithAttributes(content, attributes);
		}));
	}

	public HttpFormPart(byte[] source) {
		this(source, new UnreliableStickyInitialization<>(() -> {
			BinaryPattern pattern = new HeadBodyPattern();
			int headBody = pattern.index(source);
			if (headBody == -1) {
				throw new Exception("Part has to have a body");
			}
			String head = new String(Arrays.copyOf(source, headBody));
			String[] lines = head.split(CRLF);
			String[] firstLines = lines[0].split(SEMICOLON);
			String name = firstLines[1].trim().split("=")[1].replace("\"", "");
			String filename;
			if (firstLines.length > 2) {
				filename = firstLines[2].trim().split("=")[1].replace("\"", "");
			} else {
				filename = "";
			}
			String contentType;
			if (lines.length > 1 && !lines[1].isEmpty()) {
				String[] header = lines[1].split(COLON);
				contentType = header.length > 1 ? header[1].trim() : "";
			} else {
				contentType = TEXT_PLAIN;
			}
			byte[] body = Arrays.copyOfRange(source, headBody + pattern.value().length, source.length);
			Map<String, String> attributes = new HashMap<>();
			attributes.put("name", name);
			attributes.put("filename", filename);
			attributes.put("contentType", contentType);
			return new DefaultBinaryWithAttributes(body, attributes);
		}));
	}

	public HttpFormPart(String name, String content) {
		this(name, "", TEXT_PLAIN, content.getBytes());
	}

	public HttpFormPart(String name, String contentType, byte[] content) {
		this(name, "", contentType, content);
	}

	@Override
	public String name() throws Exception {
		return this.data.value().attribute("name");
	}

	@Override
	public String filename() throws Exception {
		return this.data.value().attribute("filename");
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
			baos.write(FIRST_LINE_PREFIX.getBytes());
			baos.write(String.format("name=\"%s\"", name()).getBytes());
			if (!filename().isEmpty()) {
				baos.write(String.format("; filename=\"%s\"", filename()).getBytes());
			}
			if (!contentType().equals(TEXT_PLAIN)) {
				baos.write(String.format("%sContent-Type: %s", CRLF, contentType()).getBytes());
			}
			baos.write((CRLF + CRLF).getBytes());
			baos.write(content());
			this.source = baos.toByteArray();
		}
		return this.source;
	}
}
