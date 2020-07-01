package com.example.wechatauthorize.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/6/28 10:18 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveUserRequest {

  private String encryptedData;
  private String iv;
  private String sessionKey;
  private String openId;

}