package reactive.endpoint;

import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class EchoControllerTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Inject
    TestClient client;

    @Test
    public void testIndex() throws Exception {
        Flowable<String> stringFlowable =
                Flowable
                        .just("test");

        assertEquals(HttpStatus.OK, client.echo("test").status());
        assertEquals(HttpStatus.OK, client.echoReactiveBlocking("test").status());
        assertEquals(HttpStatus.OK, client.echoReactive(stringFlowable).blockingGet().status());


        try (RxHttpClient client = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL())) {
            HttpRequest requestNonReactive = HttpRequest.create(HttpMethod.POST, "/echo/non-reactive").contentType(MediaType.TEXT_PLAIN).body("testText");
            assertEquals(HttpStatus.OK, client.toBlocking().exchange(requestNonReactive).status());

            HttpRequest requestReactive = HttpRequest.<String>create(HttpMethod.POST, "/echo/reactive").contentType(MediaType.TEXT_PLAIN).body("testText");
            assertEquals(HttpStatus.OK, client.toBlocking().exchange(requestReactive).status());
        }
    }
}
