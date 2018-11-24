package com.iprogrammerr.gentle.request;

public interface ConnectionCallback {

	void onSuccess(Response response);

	void onFailure(Exception exception);
}
