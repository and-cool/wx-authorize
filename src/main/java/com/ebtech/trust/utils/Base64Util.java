package com.ebtech.trust.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author andcool
 * @date 2020/7/28 3:44 下午
 */
public class Base64Util {

  /**
   * base64 加密传输
   *
   * @param value
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String encode(String value) throws UnsupportedEncodingException {
    byte[] textByte = value.getBytes("UTF-8");
    return Base64.getEncoder().encodeToString(textByte);
  }

  /**
   * base64 解密
   * @param value
   * @return
   */
  public static String decode(String value) {
    return new String(Base64.getDecoder().decode(value));
  }

}
