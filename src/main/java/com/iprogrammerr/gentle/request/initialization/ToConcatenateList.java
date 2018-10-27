package com.iprogrammerr.gentle.request.initialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ToConcatenateList<T> implements Initialization<List<T>> {

	private final List<T> base;
	private final T[] toAppend;

	@SafeVarargs
	public ToConcatenateList(List<T> base, T... toAppend) {
		this.base = base;
		this.toAppend = toAppend;
	}

	@Override
	public List<T> value() {
		List<T> concatenated = new ArrayList<>(this.base);
		concatenated.addAll(Arrays.asList(toAppend));
		return concatenated;
	}

}
