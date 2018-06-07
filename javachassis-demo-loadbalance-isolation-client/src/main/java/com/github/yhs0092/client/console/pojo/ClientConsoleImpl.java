package com.github.yhs0092.client.console.pojo;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.yhs0092.client.console.ClientConsole;
import com.github.yhs0092.client.console.RequestParam;
import com.github.yhs0092.hello.Hello;
import com.github.yhs0092.hello.HelloRequest;

@RestSchema(schemaId = "clientconsole")
@RequestMapping(path = "/clientconsole", produces = MediaType.APPLICATION_JSON)
public class ClientConsoleImpl implements ClientConsole {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientConsoleImpl.class);

  @RpcReference(schemaId = "hello", microserviceName = "loadbalance-isolation-server")
  private Hello hello;

  @RequestMapping(path = "/startRequest", method = RequestMethod.POST)
  public String startRequest(@RequestBody RequestParam requestParam) {
    LOGGER.info("start loop, name = [{}]", requestParam.getName());
    for (int i = 0; i < requestParam.getTimes(); ++i) {
      LOGGER.info("invoke#[{}], name = [{}]", i, requestParam.getName());
      String result = null;
      try {
        result = sayHello(requestParam, i);
      } catch (InvocationException e) {
        LOGGER.error("catch an InvocationException");
      } catch (Exception e) {
        LOGGER.error("catch an unexpected exception while invoking server...");
        e.printStackTrace();
      }
      LOGGER.info("invoke#[{}], name = [{}], result = [{}]", i, requestParam.getName(), result);
    }
    LOGGER.info("done, name = [{}]", requestParam.getName());
    return "done";
  }

  private String sayHello(RequestParam requestParam, int index) throws Exception {
    if (requestParam.getInterval() > 0) {
      Thread.sleep(requestParam.getInterval());
    }
    return hello.sayHello(new HelloRequest(index, requestParam.getName()));
  }

  @RequestMapping(path = "/getStatus", method = RequestMethod.GET)
  @Override
  public String getStatus() {
    LOGGER.info("getStatus is called");
    String result = hello.getStatus();
    LOGGER.info("getStatus is called, result = [{}]", result);
    return result;
  }

  @RequestMapping(path = "/setStatus", method = RequestMethod.POST)
  @Override
  public String setStatus(
      @org.springframework.web.bind.annotation.RequestParam("status") String status,
      @org.springframework.web.bind.annotation.RequestParam("times") int times,
      @org.springframework.web.bind.annotation.RequestParam("interval") long interval) {
    LOGGER.info("setStatus is called, start loop, status = [{}], time = [{}], interval = [{}]",
        status, times, interval);
    for (int i = 0; i < times; ++i) {
      if (interval > 0) {
        try {
          Thread.sleep(interval);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      String result = hello.setStatus(status);
      LOGGER.info("invoke#[{}], result = [{}]", i, result);
    }
    return "done";
  }
}
