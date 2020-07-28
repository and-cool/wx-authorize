package com.ebtech.trust.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置，生产环境下将enable设置为true
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Value("${swagger.enable}")
  private Boolean enable;

  @Bean
  public Docket buildDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .enable(enable)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ebtech.trust"))
        .paths(PathSelectors.any())
        .build();
  }

}

