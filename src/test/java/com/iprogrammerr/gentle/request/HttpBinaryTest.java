package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.binary.HttpBinary;

public final class HttpBinaryTest {

    @Test
    public void canConvert() throws Exception {
	String name = "super";
	JSONObject json = new JSONObject();
	json.put("name", name);
	assertThat(new HttpBinary(json.toString().getBytes()), new BinaryThatContainsJson(json));
    }

    @Test
    public void canBeEmpty() throws Exception {
	assertThat(new HttpBinary(new byte[0]), new EmptyBinary());
    }

    private final class BinaryThatContainsJson extends TypeSafeMatcher<HttpBinary> {

	private final JSONObject json;

	BinaryThatContainsJson(JSONObject json) {
	    this.json = json;
	}

	@Override
	public void describeTo(Description description) {
	    description.appendText("Binary contains: " + json);
	}

	@Override
	protected boolean matchesSafely(HttpBinary item) {
	    String stringed = json.toString();
	    return Arrays.equals(stringed.getBytes(), item.value()) && stringed.equals(item.stringValue())
		    && Arrays.equals(stringed.getBytes(), item.jsonValue().toString().getBytes());
	}

    }

    private final class EmptyBinary extends TypeSafeMatcher<HttpBinary> {

	@Override
	public void describeTo(Description description) {
	    description.appendText("Binary that contains nothing");
	}

	@Override
	protected boolean matchesSafely(HttpBinary item) {
	    return Arrays.equals(new byte[0], item.value()) && item.stringValue().isEmpty()
		    && "{}".equals(item.jsonValue().toString());
	}

    }

}
