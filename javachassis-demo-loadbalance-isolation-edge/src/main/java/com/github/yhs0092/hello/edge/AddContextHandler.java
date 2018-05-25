package com.github.yhs0092.hello.edge;

import java.util.Date;

import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;

public class AddContextHandler implements Handler {
  @Override
  public void handle(Invocation invocation, AsyncResponse asyncResponse) throws Exception {
    invocation.addContext("edgeContext", "edgeValue" + System.currentTimeMillis());
    invocation.next(response -> asyncResponse.handle(response));
  }
}
