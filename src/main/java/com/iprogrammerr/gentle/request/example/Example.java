package com.iprogrammerr.gentle.request.example;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Connections;
import com.iprogrammerr.gentle.request.HttpConnections;
import com.iprogrammerr.gentle.request.Response;
import com.iprogrammerr.gentle.request.template.PostRequest;

public final class Example {

	public static void main(String[] args) throws Exception {
		Connections connections = new HttpConnections();
		JSONObject post = new JSONObject();
		post.put("title", "foo");
		post.put("body", "bar");
		post.put("userId", 1);
		Response response = connections
				.response(new PostRequest("https://jsonplaceholder.typicode.com/posts",
						"application/json", post.toString().getBytes()));
		System.out.println(response.body().jsonValue());
	}
}
