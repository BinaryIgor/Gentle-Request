package com.iprogrammerr.gentle.request.mock;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.iprogrammerr.bright.server.Connection;
import com.iprogrammerr.bright.server.RequestResponseConnection;
import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.application.HttpApplication;
import com.iprogrammerr.bright.server.cors.DefaultCors;
import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;

public final class MockedServer implements AutoCloseable {

	private final Initialization<Server> server;
	private final ExecutorService executor;

	private MockedServer(Initialization<Server> server, ExecutorService executor) {
		this.server = server;
		this.executor = executor;
	}

	public MockedServer(int port, List<ConditionalRespondent> respondents) {
		this(new StickyInitialization<>(() -> {
			Application application = new HttpApplication(new DefaultCors(), respondents);
			RequestResponseProtocol protocol = new HttpOneProtocol();
			Connection connection = new RequestResponseConnection(protocol, application);
			return new Server(port, connection);
		}), Executors.newSingleThreadExecutor());

	}

	public void start() throws Exception {
		this.executor.execute(() -> {
			try {
				this.server.value().start();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		this.executor.awaitTermination(5, TimeUnit.SECONDS);
	}

	@Override
	public void close() throws Exception {
		this.server.value().stop();
	}
}
