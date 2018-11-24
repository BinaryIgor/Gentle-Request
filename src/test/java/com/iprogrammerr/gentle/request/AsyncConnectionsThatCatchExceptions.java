package com.iprogrammerr.gentle.request;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.template.GetRequest;

public final class AsyncConnectionsThatCatchExceptions extends TypeSafeMatcher<AsyncConnections> {

	private final Request request;

	public AsyncConnectionsThatCatchExceptions(Request request) {
		this.request = request;
	}

	public AsyncConnectionsThatCatchExceptions() {
		this(new GetRequest("www.mock.com"));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(AsyncConnections item) {
		boolean matched;
		try {
			CountDownLatch latch = new CountDownLatch(1);
			item.connect(this.request, new ConnectionCallback() {

				@Override
				public void onSuccess(Response response) {

				}

				@Override
				public void onFailure(Exception exception) {
					latch.countDown();
				}
			});
			matched = latch.await(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
