package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.method.DeleteMethod;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.HeadMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.PutMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.respondent.PotentialRespondent;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.gentle.request.mock.MockedServer;
import com.iprogrammerr.gentle.request.template.DeleteRequest;
import com.iprogrammerr.gentle.request.template.GetRequest;
import com.iprogrammerr.gentle.request.template.PostRequest;
import com.iprogrammerr.gentle.request.template.PutRequest;

public final class ConnectionsThatAreSendingAndResponding extends TypeSafeMatcher<Connections> {

	private static final int OK = 200;
	private static final int SEE_OTHER = 303;
	private static final int BAD_REQUEST = 400;
	private static final int INTERNAL_SERVER_ERROR = 500;
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final String HEAD = "HEAD";
	private static final String TRACE = "TRACE";
	private final int port;

	public ConnectionsThatAreSendingAndResponding(int port) {
		this.port = port;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Connections item) {
		Map<String, Respondent> urlsRespondents = new HashMap<>();
		Respondent okMirror = req -> new OkResponse(new TypedResponseBody(CONTENT_TYPE, req.body()), req.headers());
		String okUrl = "ok";
		urlsRespondents.put(okUrl, okMirror);
		Respondent notModifiedMirror = req -> new ContentResponse(SEE_OTHER,
				new TypedResponseBody(CONTENT_TYPE, req.body()), req.headers());
		String notModifiedUrl = "notModified";
		urlsRespondents.put(notModifiedUrl, notModifiedMirror);
		Respondent badRequestMirror = req -> new ContentResponse(BAD_REQUEST,
				new TypedResponseBody(CONTENT_TYPE, req.body()), req.headers());
		String badRequestUrl = "badRequest";
		urlsRespondents.put(badRequestUrl, badRequestMirror);
		Respondent internalServerErrorMirror = req -> new ContentResponse(INTERNAL_SERVER_ERROR,
				new TypedResponseBody(CONTENT_TYPE, req.body()), req.headers());
		String internalServerErrorUrl = "internalServerError";
		urlsRespondents.put(internalServerErrorUrl, internalServerErrorMirror);
		RequestMethod[] methods = new RequestMethod[] { new GetMethod(), new PostMethod(), new PutMethod(),
				new DeleteMethod(), new HeadMethod(), method -> TRACE.equals(method) };
		boolean matched;
		try (MockedServer server = new MockedServer(this.port, methodsRespondents(urlsRespondents, methods))) {
			server.start();
			matched = true;
			String baseUrl = baseUrl(this.port);
			matchMethods(OK, baseUrl + okUrl, item, HEAD, TRACE);
			matchMethods(SEE_OTHER, baseUrl + notModifiedUrl, item, HEAD, TRACE);
			matchMethods(BAD_REQUEST, baseUrl + badRequestUrl, item, HEAD, TRACE);
			// matchMethods(INTERNAL_SERVER_ERROR, baseUrl + internalServerErrorUrl, item,
			// HEAD, TRACE);
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private void matchMethods(int code, String url, Connections connections, String... additionalMethods)
			throws Exception {
		Request request = new GetRequest(url);
		assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		byte[] body = "body".getBytes();
		request = new PostRequest(url, body);
		assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		request = new PostRequest(url);
		assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		request = new PutRequest(url, body);
		assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		request = new DeleteRequest(url);
		assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		for (String method : additionalMethods) {
			request = new EmptyRequest(method, url);
			assertThat(connections.response(request), new ResponseThatIsRequest(code, request));
		}
	}

	private List<ConditionalRespondent> methodsRespondents(Map<String, Respondent> urlsRespondents,
			RequestMethod[] methods) {
		List<ConditionalRespondent> respondents = new ArrayList<>();
		for (Map.Entry<String, Respondent> entry : urlsRespondents.entrySet()) {
			for (RequestMethod rm : methods) {
				respondents.add(new PotentialRespondent(entry.getKey(), rm, entry.getValue()));
			}
		}
		return respondents;
	}

	private String baseUrl(int port) throws Exception {
		return "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/";
	}
}
