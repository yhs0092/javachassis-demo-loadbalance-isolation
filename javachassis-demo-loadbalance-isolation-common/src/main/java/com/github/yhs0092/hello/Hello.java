package com.github.yhs0092.hello;

public interface Hello {
  String sayHello(HelloRequest helloRequest, String number) throws Exception;

  String setStatus(String statusSwitch);

  String getStatus(int index);
}
