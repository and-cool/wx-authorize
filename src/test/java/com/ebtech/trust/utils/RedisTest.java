package com.ebtech.trust.utils;

import com.ebtech.trust.WechatAuthorizeApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author andcool
 * @date 2020/6/30 12:11 下午
 */
public class RedisTest extends WechatAuthorizeApplicationTests {

  @Autowired
  private RedisUtil redisUtil;

  private String key = "15611618010";

  private String value = "111111";

  private Integer expireTime = 1 * 60;

  @Test
  void getKey() {
    System.out.println(redisUtil.get(key)); //        11111 null
    System.out.println(redisUtil.getExpire(key)); //  173   -2
    System.out.println(redisUtil.hasKey(key)); //     true  false
//    redisUtil.expire("dd", 10);
  }

  @Test
  void setKey() {
    redisUtil.set(key, value, expireTime);
  }

}
