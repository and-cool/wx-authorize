package com.ebtech.trust.controller;

import com.alibaba.fastjson.JSON;
import com.ebtech.trust.dto.GetOpenIdRequest;
import com.ebtech.trust.dto.PostRequestData;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.dto.SaveUserRequest;
import com.ebtech.trust.dto.VerifyCaptcha;
import com.ebtech.trust.entity.WxUser;
import com.ebtech.trust.service.WxService;
import com.ebtech.trust.utils.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andcool
 * @date 2020/6/28 8:35 下午
 */
@RestController
@RequestMapping("/wx")
@Api(tags = "微信用户管理接口")
public class WxController {

  private final WxService wxService;

  public WxController(WxService wxService) {
    this.wxService = wxService;
  }

  /**
   * 使用openId获取微信用户信息
   *
   * @param openId 微信用户唯一openId
   * @return 微信用户信息
   */
  @GetMapping
  @ApiOperation(value = "使用openId获取用户信息.")
  public ResultData getUserinfoByOpenId(@RequestParam String openId) {
    return wxService.getUserinfoByOpenId(openId);
  }

  /**
   * 使用code获取微信用户的openId和sessionKey
   *
   * @param data 请求体
   * @return openId，sessionKey
   */
  @PostMapping(value = "/login")
  @ApiOperation(value = "使用code获取微信用户的openId和sessionKey.")
  public ResultData sendOpenId(@RequestBody PostRequestData data) {
    String decode = Base64Util.decode(data.getEncryptData());
    GetOpenIdRequest getOpenIdRequest = JSON.parseObject(decode, GetOpenIdRequest.class);
    return wxService.getUserOpenIdByCode(getOpenIdRequest.getCode());
  }

  /**
   * 通过解密前端传来的加密数据获取微信用户信息
   * @param data 请求体
   * @return 微信用户信息
   */
  @PostMapping(value = "/decrypt")
  @ApiOperation(value = "通过解密前端传来的加密数据获取微信用户信息.")
  public ResultData saveUserInfoByEncryptedData(@RequestBody PostRequestData data) {
    String decode = Base64Util.decode(data.getEncryptData());
    SaveUserRequest saveUserRequest = JSON.parseObject(decode, SaveUserRequest.class);
    return wxService.saveUserInfoByEncryptedData(
        saveUserRequest.getEncryptedData(),
        saveUserRequest.getIv(),
        saveUserRequest.getSessionKey(),
        saveUserRequest.getOpenId());
  }

  /**
   * 保存微信用户信息
   *
   * @param data 微信用户信息
   * @return 微信用户信息
   */
  @PostMapping(value = "/info")
  @ApiOperation(value = "微信用户信息")
  public ResultData saveUserInfoByUser(@RequestBody PostRequestData data) {
    String decode = Base64Util.decode(data.getEncryptData());
    WxUser wxUser = JSON.parseObject(decode, WxUser.class);
    return wxService.saveUserInfoByUser(wxUser);
  }

  /**
   * 发送手机验证码
   *
   * @param phone 手机号
   * @return 200
   */
  @GetMapping(value = "/phone/captcha")
  @ApiOperation(value = "发送手机验证码.")
  public ResultData sendPhoneCaptcha(@RequestParam String phone) {
    return wxService.sendPhoneCaptcha(phone);
  }

  /**
   * 微信用户绑定手机号
   * @param data 请求体
   * @return 200
   */
  @PostMapping(value = "/login/phone")
  @ApiOperation(value = "微信用户绑定手机号.")
  public ResultData verifyPhoneAndCaptcha(@RequestBody PostRequestData data) {
    String decode = Base64Util.decode(data.getEncryptData());
    VerifyCaptcha verifyCaptcha = JSON.parseObject(decode, VerifyCaptcha.class);
    return wxService.verifyPhoneAndCaptcha(
        verifyCaptcha.getPhone(), verifyCaptcha.getCaptcha(), verifyCaptcha.getOpenId());
  }

  /**
   * 更新微信用户信息
   *
   * @param data 微信用户信息
   * @return 微信用户信息
   */
  @PostMapping(value = "/info/update")
  @ApiOperation(value = "更新微信用户信息.")
  public ResultData updateUserInfo(@RequestBody PostRequestData data) {
    String decode = Base64Util.decode(data.getEncryptData());
    WxUser wxUser = JSON.parseObject(decode, WxUser.class);
    return wxService.upgradeUserInfoByUser(wxUser);
  }
}
