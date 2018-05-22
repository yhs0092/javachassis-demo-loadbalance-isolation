package com.github.yhs0092.hello.springmvc;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.yhs0092.hello.HelloReactive;
import com.github.yhs0092.hello.HelloRequest;

import io.vertx.core.json.JsonObject;

@RestSchema(schemaId = "reactiveHello")
@RequestMapping(path = "/reactiveHello", produces = MediaType.APPLICATION_JSON)
public class HelloReactiveImpl implements HelloReactive {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloReactiveImpl.class);

  private StatusSwitch statusSwitch = StatusSwitch.NORMAL;

  @RequestMapping(path = "/sayHello", method = RequestMethod.PUT)
  @Override
  public CompletableFuture<HelloRequest> sayHello(@RequestBody HelloRequest helloRequest) {
    LOGGER.info("sayHello is called, helloRequest = {}, status = {}", helloRequest, statusSwitch);
    CompletableFuture<HelloRequest> completableFuture = new CompletableFuture<>();

    completableFuture.complete(helloRequest);
    return completableFuture;
  }

  @RequestMapping(path = "/status", method = RequestMethod.PUT)
  @Override
  public CompletableFuture<String> setStatus(@RequestParam(name = "status") String statusSwitch) {
    LOGGER.info("set status to {}", statusSwitch);
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    try {
      this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      completableFuture.completeExceptionally(e);
    }

    completableFuture.complete("OK");
    return completableFuture;
  }

  @RequestMapping(path = "/status", method = RequestMethod.GET)
  @Override
  public CompletableFuture<String> getStatus() {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    completableFuture.complete(this.statusSwitch.toString());
    return completableFuture;
  }
}
