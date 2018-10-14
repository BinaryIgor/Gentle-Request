package com.iprogrammerr.gentle.request;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.multipart.FormPart;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class Example {

    public static void main(String[] args) {
	String url = "www.example.com";
	Requests requests = new HttpRequests();
	try {
	    Response response = requests.get(url);
	    if (response.hasProperCode()) {

	    } else {

	    }
	} catch (Exception e) {

	}

	try {
	    String body = "{\"value\": \"value\"}";
	    Response response = requests.post(url, body.getBytes(), new Header("Authorization", "Secret"));
	    if (response.hasProperCode()) {

	    } else {

	    }
	} catch (Exception e) {

	}
	byte[] binary = new byte[100];

	List<FormPart> parts = new ArrayList<>();
	FormPart firstPart = new HttpFormPart("message", "Hello!");
	FormPart secondPart = new HttpFormPart("image", "java.png", "image/png", binary);
	parts.add(firstPart);
	parts.add(secondPart);
	String boundary = "abcde";
	MultipartForm multipart = new HttpMultipartForm(boundary, parts);
	try {
	    Response response = requests.post(url, multipart.body(), multipart.header());
	    boundary = response.header("Content-Type").value().split("boundary=")[1];
	    multipart = new HttpMultipartForm(boundary, response.body());
	    parts = multipart.parts();
	    if (response.hasProperCode()) {

	    } else {

	    }
	} catch (Exception e) {

	}

    }
}
