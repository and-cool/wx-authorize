package com.example.wechatauthorize.utils;

import java.util.Random;

/**
 * @author andcool
 * @date 2020/6/30 2:19 下午
 */
public class GenerateCaptchaUtil {

  public static String generateCaptcha() {
    String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
    return verifyCode;
  }
}
