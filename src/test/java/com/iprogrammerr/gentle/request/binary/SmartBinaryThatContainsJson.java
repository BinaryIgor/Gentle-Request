package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;

public final class SmartBinaryThatContainsJson extends TypeSafeMatcher<SmartBinary> {

	private final JSONObject json;

	public SmartBinaryThatContainsJson(JSONObject json) {
		this.json = json;
	}

	@Override
	protected boolean matchesSafely(SmartBinary item) {
		String stringed = json.toString();
		boolean matched = Arrays.equals(stringed.getBytes(), item.value())
				&& stringed.equals(item.stringValue());
		if (matched) {
			try {
				matched = matched && Arrays.equals(stringed.getBytes(),
						item.jsonValue().toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				matched = false;
			}
		}
		return matched;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName() + ":" + json);
	}
}
