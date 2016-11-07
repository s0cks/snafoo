package io.github.s0cks.snafoo;

/**
 * SnafooSnackResponse
 *
 * Simple class for modeling response from the Web Service
 */
public final class SnafooResponse{
  // The response's code
  public final int code;

  // The response's message
  public final String msg;

  public SnafooResponse(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}