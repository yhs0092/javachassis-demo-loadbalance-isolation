package com.github.yhs0092.client.console;

public interface ClientConsole {
  /**
   *
   * @param requestParam request param
   * @return result of invocation
   */
  String startRequest(RequestParam requestParam);

  String getStatus();

  String setStatus(String status, int times, long interval);
}
