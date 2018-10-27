package com.iprogrammerr.gentle.request.binary;

import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

public final class HttpBinaryTest {

    @Test
    public void canConvert() throws Exception {
        String name = "super";
        JSONObject json = new JSONObject();
        json.put("name", name);
        assertThat(new HttpBinary(json.toString().getBytes()),
            new BinaryThatContainsJson(json));
    }

    @Test
    public void canBeEmpty() throws Exception {
        assertThat(new HttpBinary(new byte[0]), new BinaryThatIsEmpty());
    }
}