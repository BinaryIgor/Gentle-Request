package com.iprogrammerr.gentle.request.multipart;

import java.util.Collection;

import com.iprogrammerr.gentle.request.Header;

public interface Multipart {

    Header header();

    Collection<Part> parts();

    byte[] body() throws Exception;
}
