package com.example.wechatauthorize.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author andcool
 * @date 2020/6/30 2:30 下午
 */
@Configuration
@Component
@Getter
public class SmsConfig {

  @Value("${sms.url}")
  private String smsUrl;

  @Value("${sms.userId}")
  private String smsUserId;

  @Value("${sms.password}")
  private String smsPassword;

}
