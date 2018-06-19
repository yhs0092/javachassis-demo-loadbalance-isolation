package com.github.yhs0092.hello.springmvc;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.yhs0092.hello.Hello;
import com.github.yhs0092.hello.HelloRequest;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON)
public class HelloImpl implements Hello {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

  public static final String HELLO_PREFIX = "Hello, ";

  private StatusSwitch statusSwitch = StatusSwitch.NORMAL;

  @RequestMapping(path = "/sayHello", method = RequestMethod.PUT)
  @Override
  public String sayHello(@RequestBody HelloRequest helloRequest) throws Exception {
    LOGGER.info("sayHello is called, helloRequest = {}, status = {}", helloRequest, statusSwitch);
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

  @RequestMapping(path = "/status", method = RequestMethod.PUT)
  @Override
  public String setStatus(@RequestParam(name = "status") String statusSwitch) {
    LOGGER.info("put status to {}", statusSwitch);
    statusSwitch = statusSwitch.toUpperCase();
    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    return "PUT_OK";
  }

  @RequestMapping(path = "/status", method = RequestMethod.POST)
  public String postStatus(@RequestParam(name = "status") String statusSwitch) {
    LOGGER.info("post status to {}", statusSwitch);
    statusSwitch = statusSwitch.toUpperCase();
    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    return "POST_OK";
  }

  @RequestMapping(path = "/status", method = RequestMethod.DELETE)
  public String deleteStatus(@RequestParam(name = "status") String statusSwitch) {
    LOGGER.info("delete status to {}", statusSwitch);
    statusSwitch = statusSwitch.toUpperCase();
    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    return "DELETE_OK";
  }

  @RequestMapping(path = "/status", method = RequestMethod.GET)
  @Override
  public String getStatus() {
    LOGGER.info("getStatus is called");
    return this.statusSwitch.toString();
  }

  private String getGreeting(String name) {
    return RegistryUtils.getMicroserviceInstance().getInstanceId() + ": " + HELLO_PREFIX + name;
  }
}
