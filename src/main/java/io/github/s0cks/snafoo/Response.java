package io.github.s0cks.snafoo;

public final class Response{
  public final int code;
  public final String msg;

  public Response(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}