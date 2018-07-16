package com.github.yhs0092.hello.springmvc;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.context.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.yhs0092.hello.GenericHolder;
import com.github.yhs0092.hello.Hello;
import com.github.yhs0092.hello.HelloRequest;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON)
public class HelloImpl implements Hello {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

  public static final String HELLO_PREFIX = "Hello, ";

  private StatusSwitch statusSwitch = StatusSwitch.NORMAL;

  @Value("${server.name}")
  private String serverName;

  //  @RequestMapping(path = "/sayHello", method = RequestMethod.PUT)
  @Override
  public String sayHello(@RequestBody HelloRequest helloRequest, @RequestParam(name = "order") int orderNum)
      throws Exception {
    LOGGER.info("sayHello is called, helloRequest = {}, status = {}", helloRequest, statusSwitch);
    LOGGER.info("orderNum = [{}]", orderNum);
    switch (statusSwitch) {
      case NORMAL:
        return getGreeting(helloRequest.getName());
      case TIME_OUT: {
        Thread.sleep(1000 * 5);
        return getGreeting(helloRequest.getName());
      }
      case THROW_EXCEPTION:
        throw new Exception("sample exception");
      default:
        throw new Exception("unexpected status");
    }
  }

//  @RequestMapping(path = "/status", method = RequestMethod.PUT)
//  @Override
//  public String setStatus(@RequestParam(name = "status") String statusSwitch) {
//    LOGGER.info("set status to {}", statusSwitch);
//    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
//    return "OK";
//  }
//
//  @RequestMapping(path = "/status", method = RequestMethod.GET)
//  @Override
//  public String getStatus() {
//    return this.statusSwitch.toString();
//  }

  private String getGreeting(@RequestParam(value = "name") String name) {
    return serverName + ": " + HELLO_PREFIX + name;
  }

//  @Override
//  @RequestMapping(path = "/testVoid", method = RequestMethod.POST)
//  public void testVoid(@RequestParam(name = "param") String param) {
//    LOGGER.info("testVoid() is called, param = [{}]", param);
//  }

  @Override
//  @RequestMapping(value = "/address/ab{city}/{country}/{extra}", method = RequestMethod.GET)
  public String address(@PathVariable("city") String city, @PathVariable("country") String country, String extra) {
    LOGGER.info("city is called, city = [{}], country = [{}], extra = [{}]", city, country, extra);
    return String.format("city = [%s], country = [%s], extra = [%s]", city, country, extra);
  }

  //  @GetMapping(value = "/testList")
  public String testList(List<String> stringList) {
    LOGGER.info("testList is called, stringList = [{}]", stringList);
    return stringList.toString();
  }

  static int counter;

  @PostMapping(value = "/testGetObject")
  public String testGetObject(HelloRequest helloRequest, InvocationContext invocationContext,
      @RequestParam(name = "idx") int index, @RequestBody HelloRequest requestBody, Date today,
      GenericHolder<String> genericHolder) {
    LOGGER.info("testGetObject is called, helloRequest = [{}], index = [{}], requestBody = [{}], today = [{}]",
        helloRequest, index, requestBody, today);
    LOGGER.info("counter[{}]", ++counter);
    return helloRequest.toString();
  }
}
