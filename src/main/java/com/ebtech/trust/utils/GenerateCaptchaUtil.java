package com.ebtech.trust.utils;

import java.util.Random;

/**
 * 生成验证码
 *
 * @author andcool
 * @date 2020/6/30 2:19 下午
 */
public class GenerateCaptchaUtil {

  public static String generateCaptcha() {
    String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
    return verifyCode;
  }
}
