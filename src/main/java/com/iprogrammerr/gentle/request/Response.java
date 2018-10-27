package com.iprogrammerr.gentle.request;

import java.util.List;

import com.iprogrammerr.gentle.request.binary.SmartBinary;

public interface Response {

	int code();

	boolean hasProperCode();

	List<HttpHeader> headers();

	boolean hasHeader(String key);

	HttpHeader header(String key) throws Exception;

	SmartBinary body();
}
