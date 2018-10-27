package com.iprogrammerr.gentle.request;

import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;

public final class JsonRequestsThatAreSendingAndResponding extends TypeSafeMatcher<JsonRequests> {

	private final JSONObject response;

	public JsonRequestsThatAreSendingAndResponding(JSONObject response) {
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(JsonRequests item) {
		boolean matched = true;
		String url = "www.mock.com";
		try {
			JSONObject toSend = new JSONObject();
			toSend.put("id", 1);
			toSend.put("name", "Igor");
			Response response = item.getResponse(url);
			validateResponse(response);
			response = item.postResponse(url);
			validateResponse(response);
			response = item.postResponse(url, toSend);
			validateResponse(response);
			response = item.putResponse(url, toSend);
			validateResponse(response);
			response = item.deleteResponse(url);
			validateResponse(response);
			response = item.methodResponse("options", url, toSend);
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private void validateResponse(Response response) throws Exception {
		assertThat(response.header("Content-Type").value(),
				Matchers.equalToIgnoringCase("application/json"));
		JSONObject json = response.body().jsonValue();
		assertThat(this.response.toString().length(), Matchers.equalTo(json.toString().length()));
		assertThat(this.response.toString().getBytes(),
				Matchers.equalTo(json.toString().getBytes()));
	}

}
