package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RequestAuthTokenProvider {
  
  private static final Logger log = LoggerFactory.getLogger( RequestAuthTokenProvider.class );

  /**
   * Return auth token from the current Request Context
   * @return
   */
  public Mono<String> getTokenForCurrentRequest() {
    log.info("Receiving Context From current Request");
    return Mono.deferContextual(ctx -> {
      log.info("Inside getContextFromCurrentRequest, Retrieved context: {}", ctx);
      return ctx.get("authToken");
    });
  }
  
}
