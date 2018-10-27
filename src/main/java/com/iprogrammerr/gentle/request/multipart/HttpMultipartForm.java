package com.iprogrammerr.gentle.request.multipart;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.binary.HttpBoundaryBinaryParts;
import com.iprogrammerr.gentle.request.initialization.HttpBoundary;
import com.iprogrammerr.gentle.request.initialization.Initialization;
import com.iprogrammerr.gentle.request.initialization.StickyInitialization;

public final class HttpMultipartForm implements MultipartForm {

	private static final String TWO_HYPHENS = "--";
	private final Initialization<String> boundary;
	private byte[] parsed;
	private final List<FormPart> parts;

	private HttpMultipartForm(Initialization<String> boundary, byte[] parsed,
			List<FormPart> parts) {
		this.boundary = boundary;
		this.parsed = parsed;
		this.parts = parts;
	}

	public HttpMultipartForm(String boundary, byte[] parsed) {
		this(new StickyInitialization<>(() -> boundary), parsed, new ArrayList<>());
	}

	public HttpMultipartForm(List<FormPart> parts) {
		this(new StickyInitialization<>(new HttpBoundary()), new byte[0], parts);
	}

	@Override
	public List<FormPart> parts() {
		if (this.parts.isEmpty()) {
			List<byte[]> parts = new HttpBoundaryBinaryParts(TWO_HYPHENS + this.boundary.value())
					.parts(this.parsed);
			for (byte[] part : parts) {
				this.parts.add(new HttpFormPart(part));
			}
		}
		return this.parts;
	}

	@Override
	public Header header() {
		return new Header("Content-Type", "multipart/form-data; boundary=" + boundary.value());
	}

	@Override
	public byte[] body() throws Exception {
		if (this.parsed.length < 1) {
			this.parsed = new HttpMultipartBody(this.boundary.value(),
					this.parts.toArray(new FormPart[this.parts.size()])).content();
		}
		return this.parsed;
	}

	@Override
	public String boundary() {
		return this.boundary.value();
	}

}
