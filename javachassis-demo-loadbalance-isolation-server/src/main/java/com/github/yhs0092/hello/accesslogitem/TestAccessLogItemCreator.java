package com.github.yhs0092.hello.accesslogitem;

import org.apache.servicecomb.transport.rest.vertx.accesslog.element.AccessLogItem;
import org.apache.servicecomb.transport.rest.vertx.accesslog.element.creator.VertxExtendedAccessLogItemCreator;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.AccessLogItemLocation;

import io.vertx.ext.web.RoutingContext;

public class TestAccessLogItemCreator
    implements VertxExtendedAccessLogItemCreator {
  @Override
  public AccessLogItem<RoutingContext> create(String rawPattern, AccessLogItemLocation location) {
    if (null != location.getPlaceHolder()) {
      return null;
    }

    return new TestAccessLogItem(
        rawPattern.substring(
            location.getStart() + TestAccessLogMatcher.PREFIX.length(),
            location.getEnd() - TestAccessLogMatcher.SUFFIX.length()
        ));
  }
}
