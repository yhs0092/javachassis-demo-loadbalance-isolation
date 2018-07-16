package com.github.yhs0092.hello;

import java.util.concurrent.CompletableFuture;

public interface HelloReactive {
  CompletableFuture<HelloRequest> sayHello(HelloRequest helloRequest);

  CompletableFuture<String> setStatus(String statusSwitch);

  CompletableFuture<String> getStatus();
}
