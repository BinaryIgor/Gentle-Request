package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.HeaderThatContainsValues;

public final class MultipartContentTypeHeaderTest {

	private static final String KEY = "Content-Type";
	private static final String BOUNDARY = "abc347jh5ABC";

	@Test
	public void canHaveProperMultipartFormValues() {
		Header header = new MultipartContentTypeHeader(BOUNDARY);
		assertThat(header, new HeaderThatContainsValues(KEY.toUpperCase(), header.value()));
	}

	@Test
	public void canHaveProperMultipartValues() {
		Header header = new MultipartContentTypeHeader("mixed", BOUNDARY);
		assertThat(header, new HeaderThatContainsValues(KEY.toUpperCase(), header.value()));
	}

	@Test
	public void shouldNotBeEqualToMultipartForm() {
		Header header = new MultipartContentTypeHeader(BOUNDARY);
		assertThat(header, Matchers
				.not(new HeaderThatContainsValues(KEY.toUpperCase(), header.value().substring(1))));
	}

	@Test
	public void shouldNotBeEqualToMultipart() {
		Header header = new MultipartContentTypeHeader("alternative", BOUNDARY);
		assertThat(header, Matchers
				.not(new HeaderThatContainsValues(KEY.toUpperCase(), header.value().substring(3))));
	}
}
