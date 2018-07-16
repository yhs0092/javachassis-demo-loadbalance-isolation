package com.github.yhs0092.hello;

public class HelloRequest3 {
  private int index;

  private String name;

  private HelloRequest helloRequest;

  public HelloRequest3() {
  }

  public HelloRequest3(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public HelloRequest3 setIndex(int index) {
    this.index = index;
    return this;
  }

  public String getName() {
    return name;
  }

  public HelloRequest3 setName(String name) {
    this.name = name;
    return this;
  }

  public HelloRequest getHelloRequest() {
    return helloRequest;
  }

  public HelloRequest3 setHelloRequest(HelloRequest helloRequest) {
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
