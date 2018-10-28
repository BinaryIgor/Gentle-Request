package com.iprogrammerr.gentle.request.initalization;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.gentle.request.initialization.ArraysToList;

public final class ArraysToListTest {

	@Test
	public void canConvertArrays() {
		Integer[] array = new Integer[] { 1, 2, 3 };
		Integer[] toAppend = new Integer[] { 5, 6 };
		assertThat(new ArraysToList<Integer>(array, toAppend),
				new ArraysToListThatContainsArrays<Integer>(array, toAppend));
	}

	@Test
	public void canConvertArray() {
		String[] array = new String[] { "one", "two", "three" };
		assertThat(new ArraysToList<String>(array),
				new ArraysToListThatContainsArrays<String>(array));
	}
}