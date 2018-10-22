package com.iprogrammerr.gentle.request;

import org.json.JSONObject;

public interface JsonRequests {

    Response getResponse(String url, Header... headers) throws Exception;

    Response postResponse(String url, JSONObject json, Header... headers) throws Exception;

    Response postResponse(String url, Header... headers) throws Exception;

    Response putResponse(String url, JSONObject json, Header... headers) throws Exception;

    Response deleteResponse(String url, Header... headers) throws Exception;

    Response methodResponse(String method, String url, JSONObject json, Header... headers) throws Exception;

    Response methodResponse(String method, String url, Header... headers) throws Exception;
}
