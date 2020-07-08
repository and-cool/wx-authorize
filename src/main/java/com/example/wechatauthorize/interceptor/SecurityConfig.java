package com.example.wechatauthorize.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author andcool
 * @date 2020/7/2 9:50 上午
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

  @Autowired
  CmsInterceptor cmsInterceptor;

  /**
   * 上传地址
   */
  @Value("${file.upload.path}")
  private String filePath;
  /**
   * 显示相对地址
   */
  @Value("${file.upload.path.relative}")
  private String fileRelativePath;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(cmsInterceptor)
        .addPathPatterns("/cms/**")
        .excludePathPatterns("/cms/login", "/cms/image/carousel");
    WebMvcConfigurer.super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(filePath + "/**").
        addResourceLocations("file:" + fileRelativePath + filePath + "/");
  }
}
