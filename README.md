# Gentle-Request
Make simple requests:
```java
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
  response = requests.post(url, body.getBytes(), new Header("Authorization", "Secret"));
  if (response.hasProperCode()) {

  } else {
  
  }
} catch (Exception e) {

}
```
Or more complex:
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
