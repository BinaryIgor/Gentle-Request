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

public final class HttpMultipartForm implements MultipartForm {

	private static final String TWO_HYPHENS = "--";
	private final Initialization<String> boundary;
	private byte[] parsed;
	private final Initialization<List<FormPart>> parts;

	private HttpMultipartForm(Initialization<String> boundary, byte[] parsed,
			Initialization<List<FormPart>> parts) {
		this.boundary = boundary;
		this.parsed = parsed;
		this.parts = parts;
	}

	public HttpMultipartForm(String boundary, byte[] parsed) {
		this(new StickyInitialization<>(() -> boundary), parsed, new StickyInitialization<>(() -> {
			List<byte[]> rawParts = new HttpBoundaryBinaryParts(TWO_HYPHENS + boundary)
					.parts(parsed);
			List<FormPart> parts = new ArrayList<>(rawParts.size());
			for (byte[] rp : rawParts) {
				parts.add(new HttpFormPart(rp));
			}
			return parts;
		}));
	}

	public HttpMultipartForm(List<FormPart> parts) {
		this(new StickyInitialization<>(new HttpBoundary()), new byte[0], () -> parts);
	}

	public HttpMultipartForm(FormPart part, FormPart... parts) {
		this(new StickyInitialization<>(new HttpBoundary()), new byte[0],
				new StickyInitialization<>(new ArraysToList<>(parts, part)));
	}

	@Override
	public List<FormPart> parts() {
		return this.parts.value();
	}

	@Override
	public Header header() {
		return new MultipartContentTypeHeader(this.boundary.value());
	}

	@Override
	public byte[] body() throws Exception {
		if (this.parsed.length < 1) {
			this.parsed = new HttpMultipartBody(this.boundary.value(), this.parts.value())
					.content();
		}
		return this.parsed;
	}

	@Override
	public String boundary() {
		return this.boundary.value();
	}
}
