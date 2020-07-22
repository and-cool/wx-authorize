package com.ebtech.trust.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author andcool
 * @date 2020/7/21 3:39 下午
 */
public class EncryptPassword {

  private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  /**
   * 密码加密
   *
   * @param raw String 原始密码
   * @return String 加密后的密码
   */
  public static String encode(String raw) {
    return passwordEncoder.encode(raw);
  }

  /**
   * 密码校验
   *
   * @param raw String 原始密码
   * @param encodePassword String 加密后的密码
   * @return true | false
   */
  public static boolean matches(String raw, String encodePassword) {
    return passwordEncoder.matches(raw, encodePassword);
  }

}
