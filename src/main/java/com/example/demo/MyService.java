package com.example.demo;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MyService {

  public Mono<String> moin(final long sec) {
//    Mono.deferContextual { ctx ->
//      Mono.just(String.format("%s%s", s, ctx["myContextKey"]))
//    }

    return Mono
      .just(sec)
      .delayElement(Duration.ofSeconds(sec))
      .flatMap(s -> {
      return Mono.deferContextual(ctx -> {
          System.out.println("Defer Context " + ctx);
          System.out.println("Defer Context " + ctx.size());
          return ctx.get("key-1");
        });
      })
      .map(x -> "Moin" + Thread.currentThread() + "ms: " + System.currentTimeMillis() + ", sec: " + sec + " ctx:" + x);
  }

}
