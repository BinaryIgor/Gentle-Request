package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.gentle.request.HeaderThatContainsValues;

public final class ContentLengthHeaderTest {

	private static final String KEY = "Content-Length";

	@Test
	public void canHaveProperValues() {
		int length = 5;
		assertThat(new ContentLengthHeader(length),
				new HeaderThatContainsValues(KEY.toLowerCase(), String.valueOf(length)));
	}

	@Test
	public void shouldNotBeEqual() {
		assertThat(new ContentLengthHeader(6),
				Matchers.not(new HeaderThatContainsValues(KEY.toLowerCase(), "4")));
	}
}
