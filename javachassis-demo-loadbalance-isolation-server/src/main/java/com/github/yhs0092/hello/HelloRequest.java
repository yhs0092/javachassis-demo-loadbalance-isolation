package com.github.yhs0092.hello;

import java.util.Date;

public class HelloRequest {
  private int index;

  private String name;

  private Date date;

  private HelloRequest2 helloRequest;

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

  public Date getDate() {
    return date;
  }

  public HelloRequest setDate(Date date) {
    this.date = date;
    return this;
  }

  public HelloRequest2 getHelloRequest() {
    return helloRequest;
  }

  public HelloRequest setHelloRequest(HelloRequest2 helloRequest) {
    this.helloRequest = helloRequest;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("HelloRequest{");
    sb.append("index=").append(index);
    sb.append(", name='").append(name).append('\'');
    sb.append(", date=").append(date);
    sb.append(", helloRequest=").append(helloRequest);
    sb.append('}');
    return sb.toString();
  }
}
