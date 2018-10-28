package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public final class HttpHeaderTest {

	private static final String KEY = "Authorization";

	@Test
	public void canHaveProperValues() {
		String value = "abcSecretABC";
		assertThat(new HttpHeader(KEY, value),
				new HeaderThatContainsValues(KEY.toUpperCase(), value));
	}

	@Test
	public void shouldNotBeEqual() {
		String value = "AbCsecAT";
		assertThat(new HttpHeader(KEY, value),
				Matchers.not(new HeaderThatContainsValues(KEY.toUpperCase(), value.substring(2))));
	}
}
