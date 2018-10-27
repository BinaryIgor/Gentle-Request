package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.gentle.request.mock.MockedRequests;

public final class HttpFileRequestsTest {

	@Test
	public void canSendAndReceive() throws Exception {
		FileRequests requests = new HttpFileRequests(new MockedRequests((url, body, headers) -> {
			List<Header> h = new ArrayList<>(Arrays.asList(headers));
			h.add(new Header("Content-Length", String.valueOf(body.length)));
			return new HttpResponse(200, h, body);
		}));
		File file = new File(String.format("src%stest%sresources%sjava.png", File.separator,
				File.separator, File.separator));
		String type = "image/jpg";
		assertThat(requests, new FileRequestsThatAreSendingAndResponding(file, type));
	}

}
