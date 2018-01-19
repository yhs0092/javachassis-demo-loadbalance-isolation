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

  private String sayHello(@RequestBody RequestParam requestParam, int index) throws Exception {
    if (requestParam.getInterval() > 0) {
      Thread.sleep(requestParam.getInterval());
    }
    return hello.sayHello(new HelloRequest(index, requestParam.getName()));
  }
}
