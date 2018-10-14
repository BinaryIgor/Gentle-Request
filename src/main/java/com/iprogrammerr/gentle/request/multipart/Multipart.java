package com.iprogrammerr.gentle.request.multipart;

import java.util.List;

import com.iprogrammerr.gentle.request.Header;

public interface Multipart {

    Header header();

    List<Part> parts();

    byte[] body() throws Exception;
}
