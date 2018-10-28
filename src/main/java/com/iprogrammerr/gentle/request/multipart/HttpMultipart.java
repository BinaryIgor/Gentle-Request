package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;
import com.iprogrammerr.gentle.request.initialization.HttpBoundary;
import com.iprogrammerr.gentle.request.initialization.Initialization;
import com.iprogrammerr.gentle.request.initialization.StickyInitialization;
import com.iprogrammerr.gentle.request.template.MultipartContentTypeHeader;

public final class HttpMultipart implements Multipart {

	private static final String TWO_HYPHENS = "--";
	private final String type;
	private final Initialization<String> boundary;
	private byte[] parsed;
	private final List<Part> parts;

	private HttpMultipart(String type, Initialization<String> boundary, byte[] parsed,
			List<Part> parts) {
		this.type = type;
		this.boundary = boundary;
		this.parsed = parsed;
		this.parts = parts;
	}

	public HttpMultipart(String type, String boundary, byte[] parsed) {
		this(type, new StickyInitialization<>(() -> boundary), parsed, new ArrayList<>());
	}

	public HttpMultipart(String type, List<Part> parts) {
		this(type, new StickyInitialization<>(new HttpBoundary()), new byte[0], parts);
	}

	@Override
	public List<Part> parts() {
		if (this.parts.isEmpty()) {
			List<byte[]> parts = new HttpBoundaryBinaryParts(TWO_HYPHENS + this.boundary.value())
					.parts(this.parsed);
			for (byte[] part : parts) {
				this.parts.add(new HttpPart(part));
			}
		}
		return this.parts;
	}

	@Override
	public Header header() {
		return new MultipartContentTypeHeader(this.type, this.boundary.value());
	}

	@Override
	public byte[] body() throws Exception {
		if (this.parsed.length < 1) {
			this.parsed = new HttpMultipartBody(this.boundary.value(),
					this.parts.toArray(new Part[this.parts.size()])).content();
		}
		return this.parsed;
	}

	@Override
	public String boundary() {
		return this.boundary.value();
	}

}
