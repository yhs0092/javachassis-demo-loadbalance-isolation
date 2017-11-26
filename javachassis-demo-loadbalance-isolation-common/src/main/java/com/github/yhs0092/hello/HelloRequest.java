package com.github.yhs0092.hello;

public class HelloRequest {
  private int index;

  private String name;

  public HelloRequest() {
  }

  public HelloRequest(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public HelloRequest setIndex(int index) {
    this.index = index;
    return this;
  }

  public String getName() {
    return name;
  }

  public HelloRequest setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("HelloRequest{");
    sb.append("index=").append(index);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
