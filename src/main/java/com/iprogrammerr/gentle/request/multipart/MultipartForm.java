package com.iprogrammerr.gentle.request.multipart;

import java.util.Collection;

import com.iprogrammerr.gentle.request.Header;

public interface MultipartForm {

    Header header();

    Collection<FormPart> parts();

    byte[] body() throws Exception;
}
