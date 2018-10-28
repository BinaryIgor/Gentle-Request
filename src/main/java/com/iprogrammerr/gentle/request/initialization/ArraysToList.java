package com.iprogrammerr.gentle.request.initialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArraysToList<T> implements Initialization<List<T>> {

	private final T[] base;
	private final T[] toAppend;

	@SafeVarargs
	public ArraysToList(T[] base, T... toAppend) {
		this.base = base;
		this.toAppend = toAppend;
	}

	@Override
	public List<T> value() {
		List<T> list = new ArrayList<>(this.base.length + this.toAppend.length);
		list.addAll(Arrays.asList(this.base));
		list.addAll(Arrays.asList(this.toAppend));
		return list;
	}
}
