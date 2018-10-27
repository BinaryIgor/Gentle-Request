package com.iprogrammerr.gentle.request.initialization;

public final class UnreliableStickyInitialization<T> implements UnreliableInitialization<T> {

	private final UnreliableInitialization<T> base;
	private T value;

	public UnreliableStickyInitialization(UnreliableInitialization<T> base) {
		this.base = base;
	}

	@Override
	public T value() throws Exception {
		if (this.value == null) {
			this.value = this.base.value();
		}
		return this.value;
	}

}
