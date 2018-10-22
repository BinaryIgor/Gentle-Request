# Gentle-Request
Make simple requests:
* raw
```java
Requests requests = new HttpRequests();
try {
  Response response = requests.getResponse(url);
  if (response.hasProperCode()) {
	
  } else {

  }
} catch (Exception e) {

}
try {
  String body = "{\"value\": \"value\"}";
  response = requests.postResponse(url, body.getBytes(), new Header("Authorization", "Secret"));
  if (response.hasProperCode()) {

  } else {
  
  }
} catch (Exception e) {

}
```
* json
```java
JsonRequests requests = new HttpJsonRequests(new HttpRequests());
try {
  Response response = requests.getResponse(url);
  if (response.hasProperCode()) {
     JSONObject json = response.body().jsonValue();
  } else {

  }
} catch (Exception e) {

}
try {
  JSONObject json = new JSONObject();
  json.put("value", 444);
  response = requests.postResponse(url, json, new Header("Authorization", "Secret"));
  if (response.hasProperCode()) {
      JSONObject json = response.body().jsonValue();
  } else {
  
  }
} catch (Exception e) {

}
```
* file
```java
FileRequests requests = new HttpFileRequests(new HttpRequests());
try {
  response = requests.postResponse(url, new File("icon.jpg"),"image/jpg", new Header("Authorization", "Secret"));
  if (response.hasProperCode()) {
      
  } else {
  
  }
} catch (Exception e) {

}
```
Or more complex ones:
```java
List<FormPart> parts = new ArrayList<>();
FormPart firstPart = new HttpFormPart("message", "Hello!");
FormPart secondPart = new HttpFormPart("image", "java.png", "image/png", binary);
parts.add(firstPart);
parts.add(secondPart);
String boundary = "abcde";
MultipartForm multipart = new HttpMultipartForm(boundary, parts);
Response response = requests.post(url, multipart.body(), multipart.header());

boundary = response.header("Content-Type").value().split("boundary=")[1];
multipart = new HttpMultipartForm(boundary, response.body());
parts = multipart.parts();
```
## Maven
```xml
<dependency>
  <groupId>com.iprogrammerr</groupId>
  <artifactId>gentle-request</artifactId>
  <version>1.0.1</version>
</dependency>
```
## Gradle
```
compile 'com.iprogrammerr:gentle-request:1.0.1'