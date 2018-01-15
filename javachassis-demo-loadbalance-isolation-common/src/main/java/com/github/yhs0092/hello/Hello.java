package com.github.yhs0092.hello;

public interface Hello {
  String sayHello(HelloRequest helloRequest, int number) throws Exception;

  String setStatus(String statusSwitch);

  String getStatus();
}
