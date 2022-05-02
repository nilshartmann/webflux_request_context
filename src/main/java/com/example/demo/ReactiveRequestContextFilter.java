package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ReactiveRequestContextFilter implements WebFilter {
  final static AtomicInteger counter = new AtomicInteger();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(ctx -> {
                  System.out.println("ctx" + ctx);
                  var x = Mono.
                    fromSupplier( () -> {
                      var j = counter.incrementAndGet();
                      System.out.println("From Supplier " +  Thread.currentThread() + " int: " + j);
                      return Thread.currentThread() + " int: " + j; });
                  return ctx.put("key-1", x);
                });



    }

//  public Mono<Void> filter2(ServerWebExchange exchange, WebFilterChain chain) {
//    return chain.filter(exchange)
//      .contextWrite(ctx -> ctx.put("context", "some-context"));
//  }


  public static Mono<String> getRequest() {
    return Mono.deferContextual(v -> v.get("key-1"));
  }

}
