package com.iprogrammerr.gentle.request;

import java.util.Random;

public interface Connections {
	Response response(Request request) throws Exception;

	public class Fake implements Connections {

		private final Response response;

		public Fake(Response response) {
			this.response = response;
		}

		public Fake() {
			this(new HttpResponse(200));
		}

		@Override
		public Response response(Request request) throws Exception {
			Thread.sleep(1 + new Random().nextInt(1000));
			return this.response;
		}
	}
}
