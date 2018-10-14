package com.iprogrammerr.gentle.request;

import java.util.List;

public interface Response {

    int code();

    boolean hasProperCode();

    List<Header> headers();

    boolean hasHeader(String key);

    Header header(String key) throws Exception;

    byte[] body();
}
