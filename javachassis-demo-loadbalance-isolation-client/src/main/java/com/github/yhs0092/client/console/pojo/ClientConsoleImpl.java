package com.github.yhs0092.client.console.pojo;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.github.yhs0092.client.console.ClientConsole;
import com.github.yhs0092.client.console.RequestParam;
import com.github.yhs0092.hello.Hello;
import com.github.yhs0092.hello.HelloRequest;

@RestSchema(schemaId = "clientconsole")
@RequestMapping(path = "/clientconsole", produces = MediaType.APPLICATION_JSON)
public class ClientConsoleImpl implements ClientConsole {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientConsoleImpl.class);

  @RpcReference(schemaId = "hello", microserviceName = "demo-loadbalance-isolation:loadbalance-isolation-server")
  private Hello hello;

  @RequestMapping(path = "/startRequest", method = RequestMethod.POST)
  public String startRequest(@RequestBody RequestParam requestParam) {
    LOGGER.info("start loop, requestParam = {}", requestParam);
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
    LOGGER.info("done, requestParam = [{}]", requestParam);
    return "done";
  }

  private String sayHello(@RequestBody RequestParam requestParam, int index) throws Exception {
    if (requestParam.getInterval() > 0) {
      Thread.sleep(requestParam.getInterval());
    }
    return hello.sayHello(new HelloRequest(index, requestParam.getName()), ""+requestParam.getNumber());
  }

  @RequestMapping(path = "/startGetStatus", method = RequestMethod.POST)
  public String startGetStatus(@RequestBody RequestParam requestParam) {
    LOGGER.info("startGetStatus, requestParam = {}", requestParam);
    for (int i = 0; i < requestParam.getTimes(); ++i) {
      LOGGER.info("invoke#[{}] getStatus", i, requestParam.getName());
      String result = null;
      try {
        result = getStatus(requestParam, i);
      } catch (InvocationException e) {
        LOGGER.error("catch an InvocationException");
      } catch (Exception e) {
        LOGGER.error("catch an unexpected exception while invoking server...");
        e.printStackTrace();
      }
      LOGGER.info("invoke#[{}] getStatus, name = [{}], result = [{}]", i, requestParam.getName(), result);
    }
    LOGGER.info("startGetStatus done, requestParam = [{}]", requestParam);
    return "done";
  }

  private String getStatus(RequestParam requestParam, int index) throws Exception {
    if (requestParam.getInterval() > 0) {
      Thread.sleep(requestParam.getInterval());
    }
    return hello.getStatus(index);
  }

  @GetMapping(path = "/testTest")
  public String testTest() {
    RestTemplate restTemplate = RestTemplateBuilder.create();

    ResponseEntity<String> responseEntity = restTemplate
        .getForEntity("cse://demo-loadbalance-isolation:loadbalance-isolation-server/hello/status?index={index}",
            String.class, 12);
    System.out.println(responseEntity);

    return responseEntity.getBody();
  }
}
