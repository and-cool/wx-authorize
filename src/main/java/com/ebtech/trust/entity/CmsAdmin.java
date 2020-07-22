package com.ebtech.trust.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/7/21 3:06 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CmsAdmin {

  private Long id;
  private String username;
  private String password;
  private String phone;
  private String verifyCode;

}
