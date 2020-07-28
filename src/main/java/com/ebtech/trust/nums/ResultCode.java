package com.ebtech.trust.nums;

/**
 * 请求相应Code
 *
 * @author andcool
 * @date 2020/6/28 8:44 下午
 */
public enum ResultCode {

  SUCCESS("success", 0),

  FAIL("fail", 1),

  UNAUTHORIZED("unauthorized", 2);

  private int code;

  private String message;

  private ResultCode(String message, int code) {
    this.message = message;
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
