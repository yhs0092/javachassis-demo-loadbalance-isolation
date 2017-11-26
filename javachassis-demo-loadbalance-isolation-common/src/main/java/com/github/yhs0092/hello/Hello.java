package com.github.yhs0092.hello;

public interface Hello {
  String sayHello(HelloRequest helloRequest) throws Exception;

  String setStatus(String statusSwitch);

  String getStatus();
}
