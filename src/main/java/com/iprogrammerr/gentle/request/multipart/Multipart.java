package com.iprogrammerr.gentle.request.multipart;

import java.util.List;

import com.iprogrammerr.gentle.request.Header;

public interface Multipart {

	Header header();

	String boundary();

	List<Part> parts();

	byte[] body() throws Exception;
}
