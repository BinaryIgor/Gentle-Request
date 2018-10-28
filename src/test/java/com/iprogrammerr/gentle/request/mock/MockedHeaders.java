package com.iprogrammerr.gentle.request.mock;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.HttpHeader;

public final class MockedHeaders {

	public List<Header> mocked() {
		List<Header> headers = new ArrayList<>();
		int length = (int) (Math.random() * 10);
		for (int i = 0; i < length; ++i) {
			headers.add(new HttpHeader(randomString(), randomString()));
		}
		return headers;
	}

	private String randomString() {
		int length = 5 + (int) (Math.random() * 50);
		byte[] boundary = new byte[length];
		for (int i = 0; i < boundary.length; ++i) {
			boundary[i] = randomCharacter();
		}
		return new String(boundary, StandardCharsets.US_ASCII);
	}

	private byte randomCharacter() {
		double type = Math.random();
		byte random;
		if (type <= 0.33) {
			random = (byte) (48 + (int) (Math.random() * 10));
		} else if (type <= 0.66) {
			random = (byte) (65 + (int) (Math.random() * 26));
		} else {
			random = (byte) (97 + (int) (Math.random() * 26));
		}
		return random;
	}
}
