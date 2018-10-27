package com.iprogrammerr.gentle.request.initialization;

public final class StickyInitialization<T> implements Initialization<T> {

	private final Initialization<T> base;
	private T value;

	public StickyInitialization(Initialization<T> base) {
		this.base = base;
	}

	@Override
	public T value() {
		if (this.value == null) {
			this.value = this.base.value();
		}
		return this.value;
	}

}
