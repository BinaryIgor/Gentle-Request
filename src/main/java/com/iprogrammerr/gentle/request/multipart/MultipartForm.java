package com.iprogrammerr.gentle.request.multipart;

import com.iprogrammerr.gentle.request.Header;

public interface MultipartForm {

    Header header();

    Iterable<FormPart> parts() throws Exception;

    byte[] body() throws Exception;
}
