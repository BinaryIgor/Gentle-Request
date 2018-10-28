package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class SmartBinaryThatIsEmpty extends TypeSafeMatcher<SmartBinary> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(SmartBinary item) {
		boolean matched = Arrays.equals(new byte[0], item.value()) && item.stringValue().isEmpty();
		if (matched) {
			try {
				matched = matched && "{}".equals(item.jsonValue().toString());
			} catch (Exception e) {
				e.printStackTrace();
				matched = false;
			}
		}
		return matched;
	}
}
