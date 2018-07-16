package com.github.yhs0092.hello;

public class GenericHolder<T> {
  private String str;

  private long num;

  T data;

  public String getStr() {
    return str;
  }

  public GenericHolder<T> setStr(String str) {
    this.str = str;
    return this;
  }

  public long getNum() {
    return num;
  }

  public GenericHolder<T> setNum(long num) {
    this.num = num;
    return this;
  }

  public T getData() {
    return data;
  }

  public GenericHolder<T> setData(T data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("GenericHolder{");
    sb.append("str='").append(str).append('\'');
    sb.append(", num=").append(num);
    sb.append(", data=").append(data);
    sb.append('}');
    return sb.toString();
  }
}
