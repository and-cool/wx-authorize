package com.example.wechatauthorize.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/6/29 9:30 上午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

  private Long id;

  private String nickName;

  private Integer gender;

  private String country;

  private String province;

  private String city;

  private String phone;

  private String avatarUrl;

  private String language;

  private String openId;

  private String sessionKey;

}
