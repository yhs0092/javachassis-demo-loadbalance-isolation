package com.github.yhs0092.client.console;

public class RequestParam {
  private String name;

  /**
   * how many requests to send
   */
  private int times;

  /**
   * how many milliseconds between sending two requests
   */
  private long interval;

  public String getName() {
    return name;
  }

  public RequestParam setName(String name) {
    this.name = name;
    return this;
  }

  public int getTimes() {
    return times;
  }

  public RequestParam setTimes(int times) {
    this.times = times;
    return this;
  }

  public long getInterval() {
    return interval;
  }

  public RequestParam setInterval(long interval) {
    this.interval = interval;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("RequestParam{");
    sb.append("name='").append(name).append('\'');
    sb.append(", times=").append(times);
    sb.append(", interval=").append(interval);
    sb.append('}');
    return sb.toString();
  }
}
