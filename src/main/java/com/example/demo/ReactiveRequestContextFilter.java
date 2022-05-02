package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ReactiveRequestContextFilter implements WebFilter {
  private static final Logger log = LoggerFactory.getLogger(ReactiveRequestContextFilter.class);
  final static AtomicInteger counter = new AtomicInteger();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return chain.filter(exchange)
      .contextWrite(ctx -> {
        var x = simulateCallToReceiveAuthToken()
          // share Mono to all subscribers during this request
          // otherwise all subscribers would receive own value
          .share();
        return ctx.put("authToken", x);
      });
  }

  private static Mono<String> simulateCallToReceiveAuthToken() {
    // very poor simulation of WebClient call:
    return Mono
      .just("")
      .delayElement(Duration.ofMillis(250))
      .doOnSubscribe( s -> log.info("Subscribed to MicroService Call on Thread {}", Thread.currentThread()))
      .map(s -> {
        var j = counter.incrementAndGet();
        var contextValue = String.format("This could be an auth token received from auth service. Created on Thread %s and stored in request context. Our request number is %d",
          Thread.currentThread(), j);
        log.info("ContextFilter, created new Context with Value '{}'", contextValue);
        return contextValue;
      });
  }


}
