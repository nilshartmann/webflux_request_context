package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MyService {

  private static final Logger log = LoggerFactory.getLogger(MyService.class);

  private final RequestAuthTokenProvider requestAuthTokenProvider;

  public MyService(RequestAuthTokenProvider requestAuthTokenProvider) {
    this.requestAuthTokenProvider = requestAuthTokenProvider;
  }

  public Mono<String> moin(final long sec) {

    log.info("Business Logic starts here, delay seconds: {} on Thread {}", sec, Thread.currentThread());
    return requestAuthTokenProvider.getTokenForCurrentRequest()
      .flatMap(requestContext -> {
        log.info("Received value from request context {} on Thread {}", requestContext, Thread.currentThread());
        // do Microservice-Call with Value from request context here...
        var resonseFromMicroService = String.format("Processed GraphQL dataFetcher on Thread %s. Current Time: %s, Value of 'sec' argument: %d, Current Context: %s",
          Thread.currentThread().getName(),
          System.currentTimeMillis(),
          sec,
          requestContext);
        return Mono.just(resonseFromMicroService);
      })
      // simulate long processing time
      .delayElement(Duration.ofSeconds(sec))
      .doFinally( x -> log.info("Business Logic finished for delay seconds {} on Thread {}", sec, Thread.currentThread()));

  }

}
