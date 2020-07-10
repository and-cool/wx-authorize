package com.ebtech.trust.dto;

import com.ebtech.trust.nums.ResultCode;
import java.io.Serializable;

/**
 * @author andcool
 * @date 2020/6/28 8:41 下午
 */
public class ResultData<T> implements Serializable {

  private static final long serialVersionUID = -4577255781088498763L;
  private static final int OK = 200;
  private static final int FAIL = 500;
  private static final int UNAUTHORIZED = 403;

  private T data; //服务端数据
  private int code = OK; //状态码
  private String msg = ""; //描述信息

  //Constructors
  public ResultData() {

  }

  public ResultData(T data) {
    this.code = ResultCode.SUCCESS.getCode();
    this.msg = ResultCode.SUCCESS.getMessage();
    this.data = data;
  }

  public ResultData(T data, ResultCode resultCode) {
    this.code = resultCode.getCode();
    this.msg = resultCode.getMessage();
    this.data = data;
  }

  public ResultData(Integer code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public ResultData(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  //APIS
  public static ResultData isOk() {
    return new ResultData().status(OK).msg("success");
  }

  public static ResultData isFail() {
    return new ResultData().status(FAIL);
  }

  public static ResultData isFail(String msg) {
    return new ResultData().status(FAIL).msg(msg);
  }

  public static ResultData isFail(Throwable e) {
    return isFail().msg(e);
  }

  public ResultData isOk(T data) {
    return new ResultData(data).status(OK).msg("success");
  }

  public ResultData msg(ResultCode resultCode) {
    this.setMsg(resultCode.getMessage());
    this.setCode(resultCode.getCode());
    return this;
  }

  public ResultData msg(Throwable e) {
    this.setMsg(e.toString());
    return this;
  }

  public ResultData msg(String e) {
    this.setMsg(e);
    return this;
  }

  public ResultData data(T data) {
    this.setData(data);
    return this;
  }

  public ResultData status(int status) {
    this.setCode(status);
    return this;
  }

  //Getter&Setters
  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
