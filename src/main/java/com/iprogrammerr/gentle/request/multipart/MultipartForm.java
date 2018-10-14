package com.iprogrammerr.gentle.request.multipart;

public interface MultipartForm {

    String typeHeader();

    Iterable<FormPart> parts() throws Exception;

    byte[] body() throws Exception;
}
