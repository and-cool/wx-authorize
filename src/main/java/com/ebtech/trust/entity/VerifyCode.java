package com.ebtech.trust.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 验证码存储 实体
 *
 * @author andcool
 * @date 2020/7/20 11:25 上午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyCode {

  private String phone;

  private String code;

  private Date createAt;

  private Long expire;
}
