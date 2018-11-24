package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.DangerousConnections;

public final class AsyncHttpConnectionsTest {

	@Test
	public void shouldBeAsynchronous() {
		assertThat(new AsyncHttpConnections(new Connections.Fake()), new AsyncConnectionsThatAreAsynchronous());
	}

	@Test
	public void canCatchExcepions() {
		assertThat(new AsyncHttpConnections(new DangerousConnections()), new AsyncConnectionsThatCatchExceptions());
	}
}