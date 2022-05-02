package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MyController {

  @Autowired
  private MyService myService;

  @QueryMapping
  public Mono<String> ping(@Argument int sec) {
    return myService.moin(sec);
  }
}
