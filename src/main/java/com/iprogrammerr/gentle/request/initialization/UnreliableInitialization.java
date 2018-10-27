package com.iprogrammerr.gentle.request.initialization;

public interface UnreliableInitialization<T> {
	T value() throws Exception;
}
