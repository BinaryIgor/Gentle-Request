[![Build Status](https://travis-ci.com/Iprogrammerr/Gentle-Request.svg?branch=master)](https://travis-ci.com/Iprogrammerr/Gentle-Request)
[![Test Coverage](https://img.shields.io/codecov/c/github/iprogrammerr/gentle-request/master.svg)](https://codecov.io/gh/Iprogrammerr/Gentle-Request/branch/master)
# Gentle-Request
* *get*, *delete*, *{ method }*
```java
Connections connections = new HttpConnections();
try {
    Response response = connections.response(new GetRequest(url/*,new AuthorizationHeader(SECRET),
    	new HttpHeader("key", "value")*/));
    response = connections.response(new DeleteRequest(url/*,new AuthorizationHeader(SECRET),
    	new HttpHeader("key", "value")*/));
    response = connections.response(new EmptyRequest("HEAD", url/*,new AuthorizationHeader(SECRET),
    	new HttpHeader("key", "value")*/));
    if (response.hasSuccessCode()) {
    	byte[] raw = response.body().value();
	String string = response.body().stringValue();
	JSONObject json = response.body().jsonValue();
     } else {
	
     }
} catch (Exception e) {

}
```
* *post*, *put*, *{ method }*
```java
Connections connections = new HttpConnections();
try {
    //Headers can be added in the same way as in previous example
    //Content-Length is always added automatically
    Response response = connections.response(new PostRequest(url/*,image/jpeg,*/, new byte[44]));
    response = connections.response(new PutRequest(url/*,image/jpeg,*/, new byte[44]));
    response = connections.response(new FilledRequest(METHOD, url/*,image/jpeg*/, new byte[44]));

    //Content-Type is set to text/plain
    response = connections.response(new PostRequest(url, "secret-message"));
    response = connections.response(new PutRequest(url, "secret-message"));
    response = connections.response(new FilledRequest(METHOD, url, "secret-message"));

    JSONObject json = new JSONObject();
    json.put("id", 44);
    json.put("name", "json");
    //Content-Type is set to application/json
    response = connections.response(new PostRequest(url, json));
    response = connections.response(new PutRequest(url, json));
    response = connections.response(new FilledRequest(METHOD, url, json));

    File file = new File("java.png");
    //Content-Type is set to second argument
    response = connections.response(new PostRequest(url, "image/png", file));
    response = connections.response(new PutRequest(url, "image/png", file));
    response = connections.response(new FilledRequest(METHOD, url, "image/png", file));

    Binary binary = new PacketsBinary(new BufferedInputStream(new FileInputStream(file)), file.length());
    //Content-Type is set to multipart/form-data; boundary={generated by multipart object}
    MultipartForm multipart = new HttpMultipartForm(
        new HttpFormPart("user", "application/json", json.toString().getBytes()),
	new HttpFormPart("java", "java.png", "image/png", binary.content()));
    response = connections.response(new PostRequest(url, multipart));
    response = connections.response(new PutRequest(url, multipart));
    response = connections.response(new FilledRequest(METHOD, url, multipart));
    if (response.hasSuccessCode()) {
        byte[] raw = response.body().value();
	String string = response.body().stringValue();
	json = response.body().jsonValue();
     } else {

     }
} catch (Exception e) {

}
```
* *asynchronicity*
```java
AsyncConnections connections = new AsyncHttpConnections(new HttpConnections());
connections.connect(new GetRequest(url/*,new AuthorizationHeader(SECRET),
    new HttpHeader("key", "value")*/), new ConnectionCallback() {
			
        @Override
	public void onSuccess(Response response) {
	    if (response.hasSuccessCode()) {
	        byte[] raw = response.body().value();
	        String string = response.body().stringValue();
		JSONObject json = response.body().jsonValue();
	    } else {
			
	    }
	}
			
        @Override
	public void onFailure(Exception exception) {
	    exception.printStackTrace();
	}
});
```
## Maven
```xml
<dependency>
  <groupId>com.iprogrammerr</groupId>
  <artifactId>gentle-request</artifactId>
  <version>2.0.2</version>
</dependency>
```
## Gradle
```
compile 'com.iprogrammerr:gentle-request:2.0.2'