package com.ebtech.trust.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * cookie的config配置
 *
 * @author andcool
 * @date 2020/7/2 10:11 上午
 */
@Configuration
@Component
@Getter
public class SetCookieConfig {

  @Value("${cookie.name}")
  private String cookieName;

  @Value("${cookie.max-age}")
  private int maxAge;

}
