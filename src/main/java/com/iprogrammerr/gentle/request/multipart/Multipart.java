package com.iprogrammerr.gentle.request.multipart;

import com.iprogrammerr.gentle.request.Header;

public interface Multipart {

    Header header();

    Iterable<Part> parts() throws Exception;

    byte[] body() throws Exception;
}
