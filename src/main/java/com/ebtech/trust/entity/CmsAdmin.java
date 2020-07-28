package com.ebtech.trust.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 后台管理系统 管理员实体
 *
 * @author andcool
 * @date 2020/7/21 3:06 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "后台管理系统 管理员实体")
public class CmsAdmin {

  private Long id;

  private String username;

  private String password;

  private String phone;

  private String verifyCode;

}
