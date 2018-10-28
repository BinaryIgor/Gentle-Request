package com.iprogrammerr.gentle.request.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.iprogrammerr.gentle.request.Connections;
import com.iprogrammerr.gentle.request.HttpConnections;
import com.iprogrammerr.gentle.request.Response;
import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;
import com.iprogrammerr.gentle.request.template.GetRequest;
import com.iprogrammerr.gentle.request.template.PostRequest;

public final class Example {

	public static void main(String[] args) throws Exception {
		Connections connection = new HttpConnections();
		Response response = connection.response(new PostRequest("www.mock.com", new JSONObject()));
		response = connection.response(new GetRequest("www.mock.com"));

		List<FormPart> parts = new ArrayList<>();
		parts.add(new HttpFormPart("json", "json.json", "application/json",
				"{\"secret\": true}".getBytes()));
		parts.add(new HttpFormPart("image", "java.png", "image/png", new MockedBinary().content()));
		MultipartForm form = new HttpMultipartForm(parts);
		response = connection.response(new PostRequest("www.mock.com/1", form));
	}
}
