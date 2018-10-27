package com.iprogrammerr.gentle.request;

import java.util.List;

public interface Request {

	String url();

	String method();

	List<Header> headers();

	void addHeader(Header header);

	byte[] body() throws Exception;
}
