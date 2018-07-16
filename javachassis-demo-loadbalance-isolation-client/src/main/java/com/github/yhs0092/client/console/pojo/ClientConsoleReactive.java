package com.github.yhs0092.client.console.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.yhs0092.client.console.RequestParam;
import com.github.yhs0092.hello.HelloReactive;
import com.github.yhs0092.hello.HelloRequest;

@RestSchema(schemaId = "reactiveClient")
//@RequestMapping(path = "/reactiveClient", produces = MediaType.APPLICATION_JSON)
public class ClientConsoleReactive {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientConsoleReactive.class);

  @RpcReference(schemaId = "reactiveHello", microserviceName = "loadbalance-isolation-server")
  private HelloReactive hello;

  @RequestMapping(path = "/startRequest", method = RequestMethod.POST)
  public CompletableFuture<List<HelloRequest>> startRequest(@RequestBody RequestParam requestParam) {
    LOGGER.info("start loop, name = [{}]", requestParam.getName());
    ContextUtils.getInvocationContext().addContext("clientContext", "clientValue" + System.currentTimeMillis());
    CompletableFuture<List<HelloRequest>> completableFuture = new CompletableFuture<>();
    List<CompletableFuture<HelloRequest>> futures = new ArrayList<>(requestParam.getTimes());
    List<HelloRequest> responseList = new ArrayList<>(requestParam.getTimes());
    for (int i = 0; i < requestParam.getTimes(); ++i) {
      final int index = i;
      LOGGER.info("invoke#[{}], name = [{}]", i, requestParam.getName());
      CompletableFuture<HelloRequest> singleCall = hello.sayHello(new HelloRequest(i, requestParam.getName()));
      singleCall = singleCall.whenComplete((resp, ex) -> {
        if (null != ex) {
          ex.printStackTrace();
        }
        System.out.println(index);
        responseList.add(resp);
      });
      futures.add(singleCall);
    }

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).whenComplete((value, exception) -> {
      System.out.println(responseList);
      completableFuture.complete(responseList);
    });
    LOGGER.info("done, name = [{}]", requestParam.getName());
    return completableFuture;
  }
}
