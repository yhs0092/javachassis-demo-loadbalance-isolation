package com.github.yhs0092.client.console.pojo;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

  @RpcReference(schemaId = "hello", microserviceName = "loadbalance-isolation-server")
  private Hello hello;

  @RequestMapping(path = "/startRequest", method = RequestMethod.POST)
  public String startRequest(@RequestBody RequestParam requestParam) {
    LOGGER.info("start loop, name = [{}]", requestParam.getName());
    ContextUtils.getInvocationContext().addContext("clientContext", "clientValue" + System.currentTimeMillis());
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
    return hello.sayHello(new HelloRequest(index, requestParam.getName()), index);
  }

  private RestTemplate restTemplate = RestTemplateBuilder.create();

  @PostMapping(path = "/sayHelloByTemplate")
  public String sayHelloByTemplate(@RequestBody RequestParam requestParam, int order) {
    final HttpEntity<HelloRequest> requestEntity = new HttpEntity<>(new HelloRequest(order, requestParam.getName()));
    final ResponseEntity<String> responseEntity = restTemplate.exchange(
        "cse://loadbalance-isolation-server/hello/sayHello?order={1}", HttpMethod.PUT,
        requestEntity, String.class, order);
    return responseEntity.getBody();
  }

//  @RequestMapping(path = "/testVoid", method = RequestMethod.POST)
//  public void testVoid(@org.springframework.web.bind.annotation.RequestParam(name = "param") String param) {
//    LOGGER.info("testVoid() is called, param = [{}]", param);
//    hello.testVoid(param);
//  }
//
//  private RestTemplate restTemplate=RestTemplateBuilder.create();
//  @RequestMapping(path = "/testVoidRestTemplate", method = RequestMethod.POST)
//  public void testVoidRestTemplate(@org.springframework.web.bind.annotation.RequestParam(name = "param") String param) {
//    LOGGER.info("testVoidRestTemplate() is called, param = [{}]", param);
//    ResponseEntity<Void> voidResponseEntity = restTemplate
//        .postForEntity("cse://loadbalance-isolation-server/hello/testVoid?param=testParam", null, Void.class);
//    LOGGER.info("testVoidRestTemplate() ended, param = [{}]", param);
//  }

  @GetMapping(path = "/address")
  public String address(String city, String country, String extra) {
    LOGGER.info("address is called, city = [{}], country = [{}], extra = [{}]", city, country, extra);
    final String address = hello.address(city, country, extra);
    LOGGER.info("address = [{}]", address);
    return address;
  }

  @GetMapping(path = "/testQueryObject")
  public String testQueryObject() {
    LOGGER.info("testQueryObject is called");
    String result = hello
        .testGetObject(12, "test0", new Date(), 11, new HelloRequest().setName("test1").setDate(new Date()),
            new Date(), "str", 233, "StringData");
    LOGGER.info("result = [{}]", result);
    return result;
  }
}
