package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.gentle.request.binary.PacketsBinary;

public final class FileRequestsThatAreSendingAndResponding extends TypeSafeMatcher<FileRequests> {

	private final File body;
	private final String type;

	public FileRequestsThatAreSendingAndResponding(File body, String type) {
		this.body = body;
		this.type = type;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(FileRequests item) {
		String mockedUrl = "www.mock.com";
		boolean matched = true;
		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(this.body))) {
			byte[] toSend = new PacketsBinary(is, this.body.length()).content();
			Response response = item.postResponse(mockedUrl, this.body, this.type);
			validateResponse(toSend, response);
			response = item.putResponse(mockedUrl, this.body, this.type);
			validateResponse(toSend, response);
			response = item.methodResponse("trace", mockedUrl, this.body, this.type);
			validateResponse(toSend, response);
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private void validateResponse(byte[] sent, Response response) throws Exception {
		assertTrue(response.hasHeader("Content-Type"));
		assertTrue(response.header("Content-Type").value().equalsIgnoreCase(this.type));
		int contentLength = Integer.parseInt(response.header("Content-Length").value());
		assertTrue(sent.length == contentLength);
		assertTrue(Arrays.equals(sent, response.body().value()));
	}

}
