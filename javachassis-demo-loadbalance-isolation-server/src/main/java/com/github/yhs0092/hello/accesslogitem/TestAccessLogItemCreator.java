package com.github.yhs0092.hello.accesslogitem;

import java.util.ArrayList;
import java.util.List;

import org.apache.servicecomb.transport.rest.vertx.accesslog.element.AccessLogItem;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.AccessLogItemMeta;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.VertxRestAccessLogItemCreator;

import io.vertx.ext.web.RoutingContext;

public class TestAccessLogItemCreator implements VertxRestAccessLogItemCreator {
  private List<AccessLogItemMeta> supportedMetaList;

  public TestAccessLogItemCreator() {
    supportedMetaList = new ArrayList<>();
    supportedMetaList.add(new AccessLogItemMeta("%{", "}XtestItem"));
    // override ResponseHeaderItem
    supportedMetaList.add(new AccessLogItemMeta("%{", "}o", -1));
  }

  @Override
  public List<AccessLogItemMeta> getAccessLogItemMeta() {
    return supportedMetaList;
  }

  @Override
  public AccessLogItem<RoutingContext> createItem(AccessLogItemMeta accessLogItemMeta, String config) {
    if ("}XtestItem".equals(accessLogItemMeta.getSuffix())) {
      return accessLogParam -> config + "[" + accessLogParam.getStartMillisecond() + "]";
    }
    if ("}o".equals(accessLogItemMeta.getSuffix())) {
      return accessLogParam -> "[" + "modified item, config = " + config + "]";
    }
    return null;
  }
}
