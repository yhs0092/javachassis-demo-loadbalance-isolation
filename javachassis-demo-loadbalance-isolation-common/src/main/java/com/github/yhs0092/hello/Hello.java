package com.github.yhs0092.hello;

import java.util.Date;

public interface Hello {
  String sayHello(HelloRequest helloRequest, int orderNum) throws Exception;

  String address(String city, String country, String extra);

  String testGetObject(int index, String name, Date date, int idx, HelloRequest requestBody, Date today, String str,
      long num,String data);
//  String setStatus(String statusSwitch);
//
//  String getStatus();
//
//  public void testVoid(String param);
}
