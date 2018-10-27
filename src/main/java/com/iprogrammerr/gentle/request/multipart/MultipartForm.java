package com.iprogrammerr.gentle.request.multipart;

import java.util.List;

import com.iprogrammerr.gentle.request.HttpHeader;

public interface MultipartForm {

	HttpHeader header();

	String boundary();

	List<FormPart> parts();

	byte[] body() throws Exception;
}
