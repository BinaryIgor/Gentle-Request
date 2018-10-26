package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import com.iprogrammerr.gentle.request.binary.PacketsBinary;
import com.iprogrammerr.gentle.request.mock.MockedRequests;

public final class HttpFileRequestsTest {

    @Test
    public void canSendFile() throws Exception {
	String mockedUrl = "www.mock.com";
	FileRequests requests = new HttpFileRequests(new MockedRequests((url, body, headers) -> {
	    List<Header> h = new ArrayList<>(Arrays.asList(headers));
	    h.add(new Header("Content-Length", String.valueOf(body.length)));
	    return new HttpResponse(200, h, body);
	}));
	File file = new File(
		String.format("src%stest%sresources%sjava.png", File.separator, File.separator, File.separator));
	BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
	byte[] toSend = new PacketsBinary(is, file.length()).content();
	is.close();
	String type = "image/jpg";
	validateResponse(toSend, type, requests.postResponse(mockedUrl, file, type));
	validateResponse(toSend, type, requests.putResponse(mockedUrl, file, type));
	validateResponse(toSend, type, requests.methodResponse("options", mockedUrl, file, type));

    }

    private void validateResponse(byte[] sent, String type, Response response) throws Exception {
	assertTrue(response.hasHeader("Content-Type"));
	assertTrue(response.header("Content-Type").value().equalsIgnoreCase(type));
	int contentLength = Integer.parseInt(response.header("Content-Length").value());
	assertTrue(sent.length == contentLength);
	assertTrue(Arrays.equals(sent, response.body().value()));
    }

    private final class SendingFilesRequests extends TypeSafeMatcher<FileRequests> {

	private final Response response;
	private final String fileType;

	public SendingFilesRequests(Response response, String fileType) {
	    this.response = response;
	    this.fileType = fileType;
	}

	@Override
	public void describeTo(Description description) {
	    // TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(FileRequests item) {
	    // TODO Auto-generated method stub
	    return false;
	}

    }

}
