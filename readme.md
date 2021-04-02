> a simple httpclient based on jdk proxy
>
### 1.define a interface
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
### 2.then you can use this class as following
```java
RemoteApiService apiService =  DefaultHttpClient.newInstance(RemoteApiService.class);
String nameRes = apiService.queryName("myibu");
```