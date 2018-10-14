package com.iprogrammerr.gentle.request;

public interface Requests {

    Response get(String url, Header... headers) throws Exception;

    Response post(String url, byte[] body, Header... headers) throws Exception;

    Response put(String url, byte[] body, Header... headers) throws Exception;

    Response delete(String url, Header... headers) throws Exception;
}
