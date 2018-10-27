package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;

public final class BinaryThatContainsJson extends TypeSafeMatcher<SmartBinary> {

    private final JSONObject json;

    public BinaryThatContainsJson(JSONObject json) {
        this.json = json;
    }

    @Override
    protected boolean matchesSafely(SmartBinary item) {
        String stringed = json.toString();
        return Arrays.equals(stringed.getBytes(), item.value())
            && stringed.equals(item.stringValue()) && Arrays.equals(
                stringed.getBytes(), item.jsonValue().toString().getBytes());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Binary that contains: " + json);
    }
}
