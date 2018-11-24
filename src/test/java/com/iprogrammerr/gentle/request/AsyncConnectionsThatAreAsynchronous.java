package com.iprogrammerr.gentle.request;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.template.GetRequest;

public final class AsyncConnectionsThatAreAsynchronous extends TypeSafeMatcher<AsyncConnections> {

	private final Request request;

	public AsyncConnectionsThatAreAsynchronous(Request request) {
		this.request = request;
	}

	public AsyncConnectionsThatAreAsynchronous() {
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
			AtomicBoolean async = new AtomicBoolean(false);
			item.connect(this.request, new ConnectionCallback() {

				@Override
				public void onSuccess(Response response) {
					async.set(true);
					latch.countDown();
				}

				@Override
				public void onFailure(Exception exception) {
					async.set(true);
					latch.countDown();
				}
			});
			if (async.get()) {
				matched = false;
			} else {
				latch.await(5, TimeUnit.SECONDS);
				matched = true;
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

}
