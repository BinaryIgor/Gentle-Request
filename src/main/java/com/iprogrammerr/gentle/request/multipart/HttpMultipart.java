package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;
import com.iprogrammerr.gentle.request.initialization.ArraysToList;
import com.iprogrammerr.gentle.request.initialization.HttpBoundary;
import com.iprogrammerr.gentle.request.initialization.Initialization;
import com.iprogrammerr.gentle.request.initialization.StickyInitialization;
import com.iprogrammerr.gentle.request.template.MultipartContentTypeHeader;

public final class HttpMultipart implements Multipart {

	private static final String TWO_HYPHENS = "--";
	private final String type;
	private final Initialization<String> boundary;
	private byte[] source;
	private final Initialization<List<Part>> parts;

	private HttpMultipart(String type, Initialization<String> boundary, byte[] source,
			Initialization<List<Part>> parts) {
		this.type = type;
		this.boundary = boundary;
		this.source = source;
		this.parts = parts;
	}

	public HttpMultipart(String type, String boundary, byte[] source) {
		this(type, new StickyInitialization<>(() -> boundary), source, new StickyInitialization<>(() -> {
			List<byte[]> rawParts = new HttpBoundaryBinaryParts(TWO_HYPHENS + boundary).parts(source);
			List<Part> parts = new ArrayList<>(rawParts.size());
			for (byte[] p : rawParts) {
				parts.add(new HttpPart(p));
			}
			return parts;
		}));
	}

	public HttpMultipart(String type, List<Part> parts) {
		this(type, new StickyInitialization<>(new HttpBoundary()), new byte[0], () -> parts);
	}

	public HttpMultipart(String type, Part part, Part... parts) {
		this(type, new StickyInitialization<>(new HttpBoundary()), new byte[0],
				new StickyInitialization<>(new ArraysToList<>(parts, part)));
	}

	@Override
	public List<Part> parts() {
		return this.parts.value();
	}

	@Override
	public Header header() {
		return new MultipartContentTypeHeader(this.type, this.boundary.value());
	}

	@Override
	public byte[] body() throws Exception {
		if (this.source.length < 1) {
			this.source = new HttpMultipartBody(this.boundary.value(), this.parts.value()).content();
		}
		return this.source;
	}

	@Override
	public String boundary() {
		return this.boundary.value();
	}
}
