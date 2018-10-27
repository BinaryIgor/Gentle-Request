package com.iprogrammerr.gentle.request.multipart;

import java.util.List;

import com.iprogrammerr.gentle.request.HttpHeader;

public interface Multipart {

	HttpHeader header();

	String boundary();

	List<Part> parts();

	byte[] body() throws Exception;
}
