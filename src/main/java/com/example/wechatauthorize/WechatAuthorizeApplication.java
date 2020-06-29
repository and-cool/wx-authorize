package com.example.wechatauthorize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wechatauthorize.dao")
public class WechatAuthorizeApplication {

  public static void main(String[] args) {
    SpringApplication.run(WechatAuthorizeApplication.class, args);
  }

}
