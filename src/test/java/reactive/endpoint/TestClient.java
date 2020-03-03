package reactive.endpoint;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Client("/echo")
public interface TestClient {

    @Post(value = "/reactive", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public Single<HttpResponse<String>> echoReactive(@Body Flowable<String> data);

    @Post(value = "/reactive", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> echoReactiveBlocking(@Body String data);

    @Post(value = "/non-reactive", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> echo(@Body String data);
}
