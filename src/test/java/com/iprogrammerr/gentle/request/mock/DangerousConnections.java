package com.iprogrammerr.gentle.request.mock;

import com.iprogrammerr.gentle.request.Connections;
import com.iprogrammerr.gentle.request.Request;
import com.iprogrammerr.gentle.request.Response;

public final class DangerousConnections implements Connections {

	@Override
	public Response response(Request request) throws Exception {
		throw new Exception("This is a very dangerous connection");
	}
}
