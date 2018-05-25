package com.github.yhs0092.hello.handler;

import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;

public class AddContextHandler implements Handler {
  @Override
  public void handle(Invocation invocation, AsyncResponse asyncResponse) throws Exception {
    invocation.addContext("serverContext", "serverValue" + System.currentTimeMillis());
    invocation.next(response -> asyncResponse.handle(response));
  }
}
