package com.github.yhs0092.hello.accesslogitem;

import org.apache.servicecomb.transport.rest.vertx.accesslog.AccessLogParam;
import org.apache.servicecomb.transport.rest.vertx.accesslog.element.AccessLogItem;

import io.vertx.ext.web.RoutingContext;

public class TestAccessLogItem implements AccessLogItem<RoutingContext> {
  private final String varName;

  public TestAccessLogItem(String varName) {
    this.varName = varName;
  }

  @Override
  public String getFormattedItem(AccessLogParam<RoutingContext> accessLogParam) {
//    Invocation invocation = accessLogParam.getContextData().get(RestConst.REST_INVOCATION_CONTEXT);
//    return "[test?" + varName + "=" + invocation.getContext("edgeContext") + "]";
    return "[test?" + varName + "=" + "]";
  }
}
