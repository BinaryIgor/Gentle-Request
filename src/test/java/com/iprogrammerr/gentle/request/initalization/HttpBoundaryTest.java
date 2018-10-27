package com.iprogrammerr.gentle.request.initalization;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.gentle.request.initialization.HttpBoundary;

public final class HttpBoundaryTest {

	@Test
	public void canCreateProperBoundary() {
		assertThat(new HttpBoundary(), new HttpBoundaryThanCanCreateProperBoundaries());
	}
}
