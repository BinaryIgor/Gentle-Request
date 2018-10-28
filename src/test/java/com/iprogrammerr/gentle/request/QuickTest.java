package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

public class QuickTest {

	@Test
	public void test() {
		assertThat(3, Matchers.not(Matchers.equalTo(4)));
	}
}
