package com.iprogrammerr.gentle.request.multipart;

public interface Multipart {

    String typeHeader();

    Iterable<Part> parts() throws Exception;

    byte[] body() throws Exception;
}
