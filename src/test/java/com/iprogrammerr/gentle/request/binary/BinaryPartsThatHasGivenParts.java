package com.iprogrammerr.gentle.request.binary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.initialization.UnreliableInitialization;
import com.iprogrammerr.bright.server.initialization.UnreliableStickyInitialization;
import com.iprogrammerr.gentle.request.multipart.FormPart;

public final class BinaryPartsThatHasGivenParts extends TypeSafeMatcher<BinaryParts> {

	private final byte[] body;
	private final UnreliableInitialization<List<byte[]>> parts;

	private BinaryPartsThatHasGivenParts(byte[] body,
			UnreliableInitialization<List<byte[]>> parts) {
		this.body = body;
		this.parts = parts;
	}

	public BinaryPartsThatHasGivenParts(byte[] body, List<FormPart> parts) {
		this(body, new UnreliableStickyInitialization<>(() -> {
			List<byte[]> rawParts = new ArrayList<>(parts.size());
			for (FormPart part : parts) {
				rawParts.add(part.parsed());
			}
			return rawParts;
		}));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryParts item) {
		boolean matched = true;
		try {
			List<byte[]> parts = item.parts(this.body);
			for (int i = 0; i < parts.size(); ++i) {
				if (!Arrays.equals(parts.get(i), this.parts.value().get(i))) {
					matched = false;
					break;
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
