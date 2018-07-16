package com.github.yhs0092.hello;

public class HelloRequest2 {
  private int index;

  private String name;

  private HelloRequest3 helloRequest;

  public HelloRequest2() {
  }

  public HelloRequest2(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public HelloRequest2 setIndex(int index) {
    this.index = index;
    return this;
  }

  public String getName() {
    return name;
  }

  public HelloRequest2 setName(String name) {
    this.name = name;
    return this;
  }

  public HelloRequest3 getHelloRequest() {
    return helloRequest;
  }

  public HelloRequest2 setHelloRequest(HelloRequest3 helloRequest) {
    this.helloRequest = helloRequest;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("HelloRequest{");
    sb.append("index=").append(index);
    sb.append(", name='").append(name).append('\'');
    sb.append(", helloRequest=").append(helloRequest);
    sb.append('}');
    return sb.toString();
  }
}
