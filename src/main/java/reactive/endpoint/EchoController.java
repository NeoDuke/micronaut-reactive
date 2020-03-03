package reactive.endpoint;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Controller("/echo")
public class EchoController {

    @Post(value = "/reactive", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public Single<MutableHttpResponse<String>> echoReactive(@Body Flowable<String> data) {
        return data.collect(StringBuffer::new, StringBuffer::append)
                .map(buffer -> HttpResponse.<String>ok(buffer.toString()));
    }

    @Post(value = "/non-reactive", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public HttpResponse<String> echo(@Body String data) {
        return HttpResponse.ok(data);
    }
}