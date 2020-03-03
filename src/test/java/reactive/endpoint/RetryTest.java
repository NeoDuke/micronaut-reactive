package reactive.endpoint;

import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class RetryTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Inject
    FooClient client;

    @Test
    public void testIndex() throws Exception {
        String fooValue = client.getFooValue();

        assertEquals("Fallback", fooValue);
    }

    @Test
    public void testCircuitBreaker() throws Exception {
        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("OPEN - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("OPEN - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("WAIT - " + LocalDateTime.now().toString());
        Thread.sleep(6000);
        System.out.println("WAIT - " + LocalDateTime.now().toString());

        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());

        System.out.println("CLOSE - " + LocalDateTime.now().toString());
        assertEquals("CircuitBreaker", client.getFooCircuitBreaker());


    }
}
