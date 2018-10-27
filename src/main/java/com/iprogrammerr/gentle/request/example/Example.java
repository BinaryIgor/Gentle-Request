package com.iprogrammerr.gentle.request.example;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Response;
import com.iprogrammerr.gentle.request.HttpConnection;
import com.iprogrammerr.gentle.request.Connection;
import com.iprogrammerr.gentle.request.template.PostRequest;

public final class Example {

	public static void main(String[] args) throws Exception {
		Connection requests = new HttpConnection();
		Response response = requests.response(new PostRequest("www.mock.com", new JSONObject()));

	}
}
