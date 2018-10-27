package com.iprogrammerr.gentle.request.mock;

import com.iprogrammerr.gentle.request.HttpHeader;
import com.iprogrammerr.gentle.request.Response;

public interface MockedResponse {
	Response response(String url, byte[] body, HttpHeader... headers);
}
