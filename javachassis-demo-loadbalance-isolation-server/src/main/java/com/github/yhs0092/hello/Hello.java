package com.github.yhs0092.hello;

import java.util.Date;

import org.apache.servicecomb.swagger.invocation.context.InvocationContext;

public interface Hello {
  String sayHello(HelloRequest helloRequest, int orderNum) throws Exception;

  String address(String city, String country, String extra);

  String testGetObject(HelloRequest helloRequest, InvocationContext invocationContext, int index,
      HelloRequest requestBody, Date today, GenericHolder<String> genericHolder);
//  String setStatus(String statusSwitch);
//
//  String getStatus();
//
//  public void testVoid(String param);
}
