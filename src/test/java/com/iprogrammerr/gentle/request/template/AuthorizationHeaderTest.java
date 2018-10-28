package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.gentle.request.HeaderThatContainsValues;

public class AuthorizationHeaderTest {

	private static final String KEY = "Authorization";
	private static final String SECRET = "SECRET";

	@Test
	public void canHaveProperValues() {
		assertThat(new AuthorizationHeader(SECRET),
				new HeaderThatContainsValues(KEY.toLowerCase(), SECRET));
	}

	@Test
	public void shouldNotBeEqual() {
		assertThat(new AuthorizationHeader(SECRET),
				Matchers.not(new HeaderThatContainsValues(KEY.toUpperCase(), SECRET.substring(2))));
	}
}
