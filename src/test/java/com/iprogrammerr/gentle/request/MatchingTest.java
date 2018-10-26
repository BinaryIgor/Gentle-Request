package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

public final class MatchingTest {

    @Test
    public void assertTest() {
	Map<String, Object> map = new HashMap<>();
	Object yegor = new Object();
	map.put("Yegor", yegor);
	assertThat(map, Matchers.hasEntry("Yegor", yegor));
    }
}
