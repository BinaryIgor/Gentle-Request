package com.iprogrammerr.gentle.request.multipart;

import java.util.List;

import com.iprogrammerr.gentle.request.Header;

public interface MultipartForm {

	Header header();

	String boundary();

	List<FormPart> parts();

	byte[] body() throws Exception;
}
