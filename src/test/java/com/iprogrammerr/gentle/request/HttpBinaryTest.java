package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.binary.HttpBinary;

public final class HttpBinaryTest {

    @Test
    public void canConvert() throws Exception {
	String name = "super";
	JSONObject json = new JSONObject();
	json.put("name", name);
	HttpBinary binary = new HttpBinary(json.toString().getBytes());
	assertTrue(Arrays.equals(binary.value(), json.toString().getBytes()));
	assertTrue(json.toString().equals(binary.stringValue()));
	assertTrue(binary.jsonValue().get("name").equals(name));
    }
}
