package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
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
import com.iprogrammerr.bright.server.response.template.BadRequestResponse;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.gentle.request.mock.MockedServer;

public final class RequestsThatAreResponding extends TypeSafeMatcher<Requests> {

	private static final String HEAD = "HEAD";
	private static final String TRACE = "TRACE";
	private final int port;

	public RequestsThatAreResponding(int port) {
		this.port = port;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Requests that can handle various responses");
	}

	@Override
	protected boolean matchesSafely(Requests item) {
		String baseUrl = "http://localhost:" + this.port + "/";
		Map<String, Respondent> urlsRespondents = new HashMap<>();
		Respondent okMirror = req -> new OkResponse(new String(req.body()));
		String okUrl = "ok";
		urlsRespondents.put(okUrl, okMirror);
		Respondent notModifiedMirror = req -> new ContentResponse(303, new String(req.body()));
		String notModifiedUrl = "notModified";
		urlsRespondents.put(notModifiedUrl, notModifiedMirror);
		Respondent badRequestMirror = req -> new BadRequestResponse(new String(req.body()));
		String badRequestUrl = "badRequest";
		urlsRespondents.put(badRequestUrl, badRequestMirror);
		Respondent internalServerErrorMirror = req -> new InternalServerErrorResponse(
				new String(req.body()));
		String internalServerErrorUrl = "internalServerError";
		urlsRespondents.put(internalServerErrorUrl, internalServerErrorMirror);
		RequestMethod[] methods = new RequestMethod[] { new GetMethod(), new PostMethod(),
				new PutMethod(), new DeleteMethod(), new HeadMethod(),
				method -> TRACE.equals(method) };
		boolean matched;
		try (MockedServer server = new MockedServer(this.port,
				methodsRespondents(urlsRespondents, methods))) {
			server.start();
			matched = true;
			matchMethods(200, baseUrl + okUrl, item, HEAD, TRACE);
			matchMethods(303, baseUrl + notModifiedUrl, item, HEAD, TRACE);
			matchMethods(400, baseUrl + badRequestUrl, item, HEAD, TRACE);
			matchMethods(500, baseUrl + internalServerErrorUrl, item, HEAD, TRACE);
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

	private void matchMethods(int code, String url, Requests requests, String... additionalMethods)
			throws Exception {
		match(code, () -> requests.getResponse(url), "");
		String body = "body";
		match(code, () -> requests.postResponse(url, body.getBytes()), body);
		match(code, () -> requests.postResponse(url), "");
		match(code, () -> requests.putResponse(url, body.getBytes()), body);
		match(code, () -> requests.deleteResponse(url), "");
		for (String method : additionalMethods) {
			match(code, () -> requests.methodResponse(method, url), "");
		}
	}

	private void match(int code, Callable<Response> callable, String body) throws Exception {
		Response response = callable.call();
		assertThat(response.code(), Matchers.equalTo(code));
		if (!body.isEmpty()) {
			assertThat(response.body().stringValue(), Matchers.equalTo(body));
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

}
