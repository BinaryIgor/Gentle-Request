package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

public class QuickTest {

	@Test
	public void test() {
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(3);
		list.add(4);
		List<Integer> list2 = new ArrayList<>();
		list2.add(3);
		list2.add(2);
		assertThat(list, Matchers.hasItems(list2.toArray(new Integer[list2.size()])));
	}
}
