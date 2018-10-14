package com.iprogrammerr.gentle.request.multipart;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import com.iprogrammerr.gentle.request.binary.HeadBodyPattern;
import com.iprogrammerr.gentle.request.data.KeysValues;
import com.iprogrammerr.gentle.request.data.StringsObjects;

public final class HttpFormPart implements FormPart {

    private static final HeadBodyPattern PATTERN = new HeadBodyPattern();
    private static final String FIRST_LINE_PREFIX = "Content-Disposition: form-data; ";
    private static final String CRLF = "\r\n";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String SEMICOLON = ";";
    private static final String COLON = ":";
    private byte[] parsed;
    private final KeysValues data;

    public HttpFormPart(String name, String filename, String contentType, byte[] content) {
	this.parsed = new byte[0];
	this.data = new StringsObjects().put("name", name).put("filename", filename).put("contentType", contentType)
		.put("content", content);
    }

    public HttpFormPart(byte[] parsed) {
	this.parsed = parsed;
	this.data = new StringsObjects();
    }

    public HttpFormPart(String name, String content) {
	this(name, "", TEXT_PLAIN, content.getBytes());
    }

    public HttpFormPart(String name, String contentType, byte[] content) {
	this(name, "", contentType, content);
    }

    @Override
    public String name() throws Exception {
	if (this.data.isEmpty()) {
	    read();
	}
	return this.data.value("name", String.class);
    }

    @Override
    public String filename() throws Exception {
	if (this.data.isEmpty()) {
	    read();
	}
	return this.data.value("filename", String.class);
    }

    @Override
    public String contentType() throws Exception {
	if (this.data.isEmpty()) {
	    read();
	}
	return this.data.value("contentType", String.class);
    }

    @Override
    public byte[] content() throws Exception {
	if (this.data.isEmpty()) {
	    read();
	}
	return this.data.value("content", byte[].class);
    }

    @Override
    public byte[] parsed() throws Exception {
	if (this.parsed.length < 1) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream(this.content().length);
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
	    this.parsed = baos.toByteArray();
	}
	return this.parsed;
    }

    private void read() throws Exception {
	int headBody = PATTERN.index(this.parsed);
	if (headBody == -1) {
	    throw new Exception("Part has to have a body");
	}
	String head = new String(Arrays.copyOf(this.parsed, headBody));
	String[] lines = head.split(CRLF);
	String[] firstLines = lines[0].split(SEMICOLON);
	String name = valueFromPair(firstLines[1].trim());
	String filename;
	if (firstLines.length > 2) {
	    filename = valueFromPair(firstLines[2].trim());
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
	byte[] body = Arrays.copyOfRange(this.parsed, headBody + PATTERN.value().length, this.parsed.length);
	this.data.put("name", name).put("filename", filename).put("contentType", contentType).put("content", body);
    }

    private String valueFromPair(String keyValue) {
	String value;
	String[] split = keyValue.split("=");
	if (split.length < 2) {
	    value = "";
	} else {
	    value = split[1].substring(1, split[1].length() - 1);
	}
	return value;
    }

}
