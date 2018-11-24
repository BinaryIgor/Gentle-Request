package com.iprogrammerr.gentle.request.mock;

import java.util.List;

import com.iprogrammerr.bright.server.BrightServer;
import com.iprogrammerr.bright.server.Connection;
import com.iprogrammerr.bright.server.RequestResponseConnection;
import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.application.HttpApplication;
import com.iprogrammerr.bright.server.cors.DefaultPreflightCors;
import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;

public final class MockedServer implements AutoCloseable, Server {

	private final Initialization<Server> server;

	private MockedServer(Initialization<Server> server) {
		this.server = server;
	}

	public MockedServer(int port, List<ConditionalRespondent> respondents) {
		this(new StickyInitialization<>(() -> {
			Application application = new HttpApplication(new DefaultPreflightCors(), respondents);
			RequestResponseProtocol protocol = new HttpOneProtocol();
			Connection connection = new RequestResponseConnection(protocol, application);
			return new BrightServer(port, connection);
		}));

	}

	public void start() throws Exception {
		this.server.value().start();
	}

	@Override
	public void close() throws Exception {
		this.server.value().stop();
	}

	@Override
	public boolean isRunning() {
		return this.server.value().isRunning();
	}

	@Override
	public void stop() {
		this.server.value().stop();
	}
}
