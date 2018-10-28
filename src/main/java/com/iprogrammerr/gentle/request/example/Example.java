package com.iprogrammerr.gentle.request.example;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Connections;
import com.iprogrammerr.gentle.request.HttpConnections;
import com.iprogrammerr.gentle.request.Response;
import com.iprogrammerr.gentle.request.template.GetRequest;
import com.iprogrammerr.gentle.request.template.PostRequest;

public final class Example {

	public static void main(String[] args) throws Exception {
		Connections connections = new HttpConnections();
		Response response = connections.response(new PostRequest("www.mock.com", new JSONObject()));
		response = connections.response(new GetRequest("www.mock.com"));

	}
}
