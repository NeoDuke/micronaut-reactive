package reactive.endpoint;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.CircuitBreaker;
import io.micronaut.retry.annotation.Retryable;

@Client("http://foo.ess/")
public interface FooClient extends FooOperations {
    @Override
    @Get
    @Retryable(attempts = "5", delay = "1s")
    public String getFooValue();

    @Override
    @Get
    @CircuitBreaker(attempts = "1", reset = "5s")
    public String getFooCircuitBreaker();
}
