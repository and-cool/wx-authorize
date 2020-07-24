package com.ebtech.trust.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author andcool
 * @date 2020/7/23 5:25 下午
 */
@Configuration
public class DomainConfig {

  /**
   * 解决问题：
   * There was an unexpected error (type=Internal Server Error, status=500).
   * An invalid domain [.xxx.com] was specified for this cookie
   *
   * @return
   */
  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
    return (factory) -> factory.addContextCustomizers(
        (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
  }
}
