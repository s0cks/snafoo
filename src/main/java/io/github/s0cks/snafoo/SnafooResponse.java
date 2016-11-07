package io.github.s0cks.snafoo;

public final class SnafooResponse{
  public final int code;
  public final String msg;

  public SnafooResponse(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}