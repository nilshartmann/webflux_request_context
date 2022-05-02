# Add Context to Reactive Requests with WebFlux

## Example

* Start the application
* Open Graphiql: http://localhost:5544/graphiql
* Run Query:
```graphql

query { 
    ping
    p1: ping(sec:3)
    p2: ping(sec:2)
}

```

This query simulates three fields, all fields are handled reactive.

See logging on the console for more details.

When the graphql request comes in, one Context is created (see: `ReactiveRequestContextFilter`), 
that can be retrieved in the handler functions (`MyService` in this case).

The value for the context is determined reactive too (in real life could be a request using WebClient)

Note: there is _one context per request_, not per field. Look at `Our request number is` in the graphql response.
It should be the same value for all fields
