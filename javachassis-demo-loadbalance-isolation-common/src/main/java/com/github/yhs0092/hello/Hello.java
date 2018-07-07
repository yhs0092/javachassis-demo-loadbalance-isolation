package com.github.yhs0092.hello;

public interface Hello {
  String sayHello(HelloRequest helloRequest, int orderNum) throws Exception;

  String address(String city, String country, String extra);

//  String setStatus(String statusSwitch);
//
//  String getStatus();
//
//  public void testVoid(String param);
}
