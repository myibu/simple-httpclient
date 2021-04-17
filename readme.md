
# simple-httpclient

a simple httpclient based on jdk proxy and annotation


## Implements

Based on jdk proxy, we create a proxy object for a interface which annotated by HttpClient annotation, and send http request according the param, return the http response to the method finally.

## Installation
```bash
<dependency>
  <groupId>com.github.myibu</groupId>
  <artifactId>simple-httpclient</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Examples

Assume than you have defined a interface annotated with HttpClient annotation as following:
```java
@HttpClient(
        name = "remoteApiService",
        url = "http://localhost:8086"
)
public interface RemoteApiService {
    @HttpRequestMethodMapping(value = "", method = HttpRequestMethod.GET)
    String query(@HttpRequestParam String id);
 
    @HttpRequestMethodMapping(value = "/api/type", method = HttpRequestMethod.GET)
    ApiQueryResult queryResult(@HttpRequestBody(required = false) Person person);
 
    @HttpRequestMethodMapping(value = "/api/{name}", method = HttpRequestMethod.GET)
    String queryName(@HttpPathVariable(value = "name", required = false) String name);
}
```
then you can use this class to send http request like this:
```java
RemoteApiService apiService =  DefaultHttpClient.newInstance(RemoteApiService.class);
String nameRes = apiService.queryName("myibu");
```
