package com.ebtech.trust.controller;

import com.ebtech.trust.dto.GetOpenIdRequest;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.dto.SaveUserRequest;
import com.ebtech.trust.dto.VerifyCaptcha;
import com.ebtech.trust.entity.User;
import com.ebtech.trust.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/user")
@Api(tags = "User operator interface")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @ApiOperation(value = "Get userinfo by openId.")
  public ResultData getUserinfoByOpenId(@RequestParam String openId) {
    return userService.getUserinfoByOpenId(openId);
  }

  @PostMapping(value = "/login/wx")
  @ApiOperation(value = "Get openId and sessionKey by code.")
  public ResultData sendOpenId(@RequestBody GetOpenIdRequest getOpenIdRequest) {
    System.out.println(String.valueOf(getOpenIdRequest.getCode()));
    return userService.getUserOpenIdByCode(getOpenIdRequest.getCode());
  }

  @PostMapping(value = "/decrypt")
  @ApiOperation(value = "Save user information by decrypting data.")
  public ResultData saveUserInfoByEncryptedData(@RequestBody SaveUserRequest saveUserRequest) {

    return userService.saveUserInfoByEncryptedData(
        saveUserRequest.getEncryptedData(),
        saveUserRequest.getIv(),
        saveUserRequest.getSessionKey(),
        saveUserRequest.getOpenId());
  }

  @PostMapping(value = "/info")
  @ApiOperation(value = "Save user information")
  public ResultData saveUserInfoByUser(@RequestBody User user) {
    return userService.saveUserInfoByUser(user);
  }

  @GetMapping(value = "/phone/captcha")
  @ApiOperation(value = "Send captcha code to phone number.")
  public ResultData sendPhoneCaptcha(@RequestParam String phone) {
    return userService.sendPhoneCaptcha(phone);
  }

  @PostMapping(value = "/login/phone")
  @ApiOperation(value = "Log in to verify phone number and captcha.")
  public ResultData verifyPhoneAndCaptcha(@RequestBody VerifyCaptcha verifyCaptcha) {
    return userService.verifyPhoneAndCaptcha(
        verifyCaptcha.getPhone(), verifyCaptcha.getCaptcha(), verifyCaptcha.getOpenId());
  }

  @PostMapping(value = "/info/update")
  @ApiOperation(value = "Update user info.")
  public ResultData updateUserInfo(@RequestBody User user) {
    System.out.println(String.valueOf(user));
    return userService.upgradeUserInfoByUser(user);
  }
}
