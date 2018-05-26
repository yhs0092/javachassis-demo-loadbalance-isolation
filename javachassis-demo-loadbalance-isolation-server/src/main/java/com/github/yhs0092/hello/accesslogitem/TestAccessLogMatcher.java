package com.github.yhs0092.hello.accesslogitem;

import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.AccessLogItemLocation;
import org.apache.servicecomb.transport.rest.vertx.accesslog.parser.matcher.AccessLogItemMatcher;

public class TestAccessLogMatcher implements AccessLogItemMatcher {

  public static final String PREFIX = "%{";

  public static final String SUFFIX = "}XtestItem";

  @Override
  public AccessLogItemLocation match(String rawPattern, int offset) {
    int end = rawPattern.indexOf(SUFFIX, offset);
    if (end < 0) {
      return null;
    }

    int start = rawPattern.lastIndexOf(PREFIX, end);
    if (start < 0) {
      return null;
    }

    end += SUFFIX.length();
    return new AccessLogItemLocation().setStart(start).setEnd(end);
  }
}
