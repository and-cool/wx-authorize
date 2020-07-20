package com.ebtech.trust.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "User entity")
public class User {

  @ApiModelProperty(value = "1", example = "User id，auto increment")
  private Long id;

  @ApiModelProperty(value = "name", example = "User name")
  private String name;

  @ApiModelProperty(value = "nickName", example = "【wx info】User name")
  private String nickName;

  @ApiModelProperty(value = "1", example = "【wx info】User gender")
  private Integer gender;

  @ApiModelProperty(value = "China", example = "【wx info】User country")
  private String country;

  @ApiModelProperty(value = "Beijing", example = "【wx info】User province")
  private String province;

  @ApiModelProperty(value = "Chaoyang", example = "【wx info】User city")
  private String city;

  @ApiModelProperty(value = "Wangjing Source", example = "User address")
  private String address;

  @ApiModelProperty(value = "R&D", example = "User occupation")
  private String occupation;

  @ApiModelProperty(value = "13601692408", example = "【wx info】User phone")
  private String phoneNumber;

  @ApiModelProperty(value = "13601692408", example = "【wx info】User purePhoneNumber")
  private String purePhoneNumber;

  @ApiModelProperty(value = "86", example = "【wx info】User countryCode")
  private String countryCode;

  @ApiModelProperty(value = "80800234", example = "User telephone")
  private String telephone;

  @ApiModelProperty(value = "test@163.com", example = "User email")
  private String email;

  @ApiModelProperty(value = "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJDKicicxOkplUeWSpQfm4oxt5Vvs9PTUKP9Dwm0HMByxlo5Lbr4po8GPrTExicuiaeaLgSC5yhVYJfbQ/132", example = "【wx info】User avatarUrl")
  private String avatarUrl;

  @ApiModelProperty(value = "zh_CN", example = "【wx info】User local language")
  private String language;

  @ApiModelProperty(value = "xxxx", example = "【wx info】User openId")
  private String openId;

  @ApiModelProperty(value = "xxxx", example = "【wx info】User session key")
  private String sessionKey;

  @ApiModelProperty(value = "是", example = "User is group customer")
  private String isGroupCustomer;

}
