package com.iprogrammerr.gentle.request;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncHttpConnections implements AsyncConnections {

	private final Executor executor;
	private final Connections connections;

	public AsyncHttpConnections(Executor executor, Connections connections) {
		this.executor = executor;
		this.connections = connections;
	}

	public AsyncHttpConnections(Executor executor) {
		this(executor, new HttpConnections());
	}

	public AsyncHttpConnections(Connections connections) {
		this(Executors.newCachedThreadPool(), connections);
	}

	public AsyncHttpConnections() {
		this(new HttpConnections());
	}

	@Override
	public void connect(Request request, ConnectionCallback callback) {
		this.executor.execute(() -> {
			try {
				callback.onSuccess(this.connections.response(request));
			} catch (Exception e) {
				callback.onFailure(e);
			}
		});
	}
}
