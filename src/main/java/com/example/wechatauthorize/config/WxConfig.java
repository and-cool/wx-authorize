package com.example.wechatauthorize.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author andcool
 * @date 2020/6/28 8:52 下午
 */
@Configuration
@Component
public class WxConfig {

  @Value("${wx.appId}")
  private String appId;

  @Value("${wx.appSecret}")
  private String appSecret;

  @Value("${wx.token}")
  private String token;

  @Value("${wx.aesKey}")
  private String aesKey;

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public String getToken() {
    return token;
  }

  public String getAesKey() {
    return aesKey;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage() {
    WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
    configStorage.setAppId(appId);
    configStorage.setSecret(appSecret);
    configStorage.setToken(token);
    configStorage.setAesKey(aesKey);
    return configStorage;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxMpService wxMpService(WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage) {
    WxMpService wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
    return wxMpService;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
    return new WxMpMessageRouter(wxMpService);
  }
}
