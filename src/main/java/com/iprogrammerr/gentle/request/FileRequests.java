package com.iprogrammerr.gentle.request;

import java.io.File;

public interface FileRequests {

    Response postResponse(String url, File file, String type, Header... headers) throws Exception;

    Response putResponse(String url, File file, String type, Header... headers) throws Exception;

    Response methodResponse(String method, String url, File file, String type, Header... headers) throws Exception;
}
