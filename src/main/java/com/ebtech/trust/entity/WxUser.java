package com.ebtech.trust.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 微信用户实体
 *
 * @author andcool
 * @date 2020/6/29 9:30 上午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "微信用户实体")
public class WxUser {

  @ApiModelProperty(value = "1", example = "微信用户id，自增长")
  private Long id;

  @ApiModelProperty(value = "name", example = "用户名称")
  private String name;

  @ApiModelProperty(value = "nickName", example = "微信用户 - 昵称")
  private String nickName;

  @ApiModelProperty(value = "1", example = "微信用户 - 性别")
  private Integer gender;

  @ApiModelProperty(value = "China", example = "微信用户 - 国家")
  private String country;

  @ApiModelProperty(value = "Beijing", example = "微信用户 - 省会")
  private String province;

  @ApiModelProperty(value = "Chaoyang", example = "微信用户 - 城市")
  private String city;

  @ApiModelProperty(value = "Wangjing Source", example = "用户 - 地址")
  private String address;

  @ApiModelProperty(value = "R&D", example = "用户 - 职业")
  private String occupation;

  @ApiModelProperty(value = "13601692408", example = "微信用户 - 手机号")
  private String phoneNumber;

  @ApiModelProperty(value = "13601692408", example = "微信用户 - 区号手机号")
  private String purePhoneNumber;

  @ApiModelProperty(value = "86", example = "微信用户 - 区号")
  private String countryCode;

  @ApiModelProperty(value = "80800234", example = "用户 - 固话")
  private String telephone;

  @ApiModelProperty(value = "test@163.com", example = "用户 - 邮箱")
  private String email;

  @ApiModelProperty(value = "xxx", example = "微信用户 - 头像地址")
  private String avatarUrl;

  @ApiModelProperty(value = "zh_CN", example = "微信用户 - 使用的语言")
  private String language;

  @ApiModelProperty(value = "xxxx", example = "微信用户 - openId")
  private String openId;

  @ApiModelProperty(value = "xxxx", example = "微信用户 - session key")
  private String sessionKey;

  @ApiModelProperty(value = "是", example = "微信用户 - 是否是集团客户")
  private String isGroupCustomer;

}
