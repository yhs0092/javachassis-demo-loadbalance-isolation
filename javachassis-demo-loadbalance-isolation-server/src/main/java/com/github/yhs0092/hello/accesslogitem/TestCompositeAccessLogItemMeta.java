package com.github.yhs0092.hello.accesslogitem;

import java.util.ArrayList;
import java.util.List;

import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.CompositeVertxRestAccessLogItemMeta;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.VertxRestAccessLogItemMeta;

public class TestCompositeAccessLogItemMeta extends CompositeVertxRestAccessLogItemMeta {
  private static final List<VertxRestAccessLogItemMeta> META_LIST = new ArrayList<>();

  static {
    META_LIST.add(new VertxRestAccessLogItemMeta("%{", "}o",
        config -> (accessLogParam -> "[" + "modified item, config = " + config + "]"),
        -1));
    META_LIST.add(new VertxRestAccessLogItemMeta("%{", "}XtestItem",
        config -> (accessLogParam -> config + "[" + accessLogParam.getStartMillisecond() + "]"),
        -1));
  }

  @Override
  public List<VertxRestAccessLogItemMeta> getAccessLogItemMetas() {
    return META_LIST;
  }
}
