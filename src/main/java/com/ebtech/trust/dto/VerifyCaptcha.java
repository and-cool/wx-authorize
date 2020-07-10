package com.ebtech.trust.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/7/1 5:12 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyCaptcha {

  private String phone;
  private String captcha;
  private String openId;
}
