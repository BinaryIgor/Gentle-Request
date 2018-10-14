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
