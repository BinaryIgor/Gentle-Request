package com.iprogrammerr.gentle.request.matching;

import java.util.concurrent.Callable;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ThrowsException<T> extends TypeSafeMatcher<Callable<T>> {

    @Override
    public void describeTo(Description description) {
	description.appendText("Callable that throws Exception");
    }

    @Override
    protected boolean matchesSafely(Callable<T> item) {
	boolean thrown;
	try {
	    item.call();
	    thrown = false;
	} catch (Exception e) {
	    thrown = true;
	}
	return thrown;
    }

}
