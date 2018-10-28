package com.iprogrammerr.gentle.request.initialization;

import java.nio.charset.StandardCharsets;

public final class HttpBoundary implements Initialization<String> {

	@Override
	public String value() {
		int length = 25 + (int) (Math.random() * 45);
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
