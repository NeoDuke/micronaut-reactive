package reactive.endpoint;


import io.micronaut.retry.annotation.Fallback;

@Fallback

public class FooFallback implements FooOperations {
    @Override
    public String getFooValue() {
        return "Fallback";
    }

    @Override
    public String getFooCircuitBreaker() {
        return "CircuitBreaker";
    }
}