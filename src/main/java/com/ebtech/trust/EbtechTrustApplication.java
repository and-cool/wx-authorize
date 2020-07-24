package com.ebtech.trust;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ebtech.trust.dao")
public class EbtechTrustApplication {

  public static void main(String[] args) {
    SpringApplication.run(EbtechTrustApplication.class, args);
  }

}
