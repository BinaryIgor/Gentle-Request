package com.iprogrammerr.gentle.request.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryThatIsEmpty extends TypeSafeMatcher<SmartBinary> {

    @Override
    public void describeTo(Description description) {
        description.appendText("Binary that contains nothing");
    }

    @Override
    protected boolean matchesSafely(SmartBinary item) {
        return Arrays.equals(new byte[0], item.value())
            && item.stringValue().isEmpty()
            && "{}".equals(item.jsonValue().toString());
    }
}
