package com.iprogrammerr.gentle.request.initalization;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.initialization.ArraysToList;

public final class ArraysToListThatContainsArrays<T> extends TypeSafeMatcher<ArraysToList<T>> {

	private final T[] base;
	private final T[] toAppend;

	@SafeVarargs
	public ArraysToListThatContainsArrays(T[] base, T... toAppend) {
		this.base = base;
		this.toAppend = toAppend;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(ArraysToList<T> item) {
		List<T> arrays = item.value();
		return containsAll(arrays, this.base) && containsAll(arrays, this.toAppend);
	}

	private boolean containsAll(List<T> container, T[] contained) {
		for (T t : contained) {
			if (!container.contains(t)) {
				return false;
			}
		}
		return true;
	}
}
