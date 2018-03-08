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
import com.netflix.config.DynamicProperty;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON)
public class HelloImpl implements Hello {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

  public static final String HELLO_PREFIX = "Hello, ";

  private StatusSwitch statusSwitch = StatusSwitch.NORMAL;

  public HelloImpl() {
    DynamicProperty testConfig = DynamicProperty.getInstance("testConfig");
    testConfig.addCallback(() -> {
      LOGGER.info("configuration[{}] changed to [{}]!!!!!!", testConfig.getName(), testConfig.getString());
    });
  }

  @RequestMapping(path = "/sayHello", method = RequestMethod.PUT)
  @Override
  public String sayHello(@RequestBody HelloRequest helloRequest, @RequestParam(name = "number") int number)
      throws Exception {
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
    LOGGER.info("set status to {}", statusSwitch);
    this.statusSwitch = StatusSwitch.valueOf(statusSwitch);
    return "OK";
  }

  @RequestMapping(path = "/status", method = RequestMethod.GET)
  @Override
  public String getStatus(@RequestParam(name = "index") int index) {
    LOGGER.info("getStatus is called, count = {}", index);
    return this.statusSwitch.toString();
  }

  private String getGreeting(@RequestParam(value = "name") String name) {
    return RegistryUtils.getMicroserviceInstance().getInstanceId() + ": " + HELLO_PREFIX + name;
  }
}
