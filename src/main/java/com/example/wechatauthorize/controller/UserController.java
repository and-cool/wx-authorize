package com.example.wechatauthorize.controller;

import com.example.wechatauthorize.dto.GetOpenIdRequest;
import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.dto.SaveUserRequest;
import com.example.wechatauthorize.entity.User;
import com.example.wechatauthorize.service.UserService;
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
    ResultData resultData = userService.getUserinfoByOpenId(openId);
    return resultData;
  }

  @PostMapping(value = "/login/wx")
  @ApiOperation(value = "Get openId and sessionKey by code.")
  public ResultData sendOpenId(@RequestBody GetOpenIdRequest getOpenIdRequest) {
    System.out.println(String.valueOf(getOpenIdRequest.getCode()));
    ResultData resultData = userService.getUserOpenIdByCode(getOpenIdRequest.getCode());
    return resultData;
  }

  @PostMapping(value = "/decrypt")
  @ApiOperation(value = "Save user information by decrypting data.")
  public ResultData saveUserInfoByEncryptedData(@RequestBody SaveUserRequest saveUserRequest) {
    ResultData resultData = userService.saveUserInfoByEncryptedData(
        saveUserRequest.getEncryptedData(),
        saveUserRequest.getIv(),
        saveUserRequest.getSessionKey());
    return resultData;
  }

  @PostMapping(value = "/info")
  @ApiOperation(value = "Save user information")
  public ResultData saveUserInfoByUser(@RequestBody User user) {
    ResultData resultData = userService.saveUserInfoByUser(user);
    return resultData;
  }

  @GetMapping(value = "/phone/captcha")
  @ApiOperation(value = "Send captcha code to phone number.")
  public ResultData sendPhoneCaptcha(@RequestParam String phone) {
    ResultData resultData = userService.sendPhoneCaptcha(phone);
    return resultData;
  }

  @PostMapping(value = "/login/phone")
  @ApiOperation(value = "Log in to verify phone number and captcha.")
  public ResultData verifyPhoneAndCaptcha(@RequestParam String phone,
      @RequestParam String captcha) {
    ResultData resultData = userService.verifyPhoneAndCaptcha(phone, captcha);
    return resultData;
  }

  @PatchMapping(value = "/info")
  @ApiOperation(value = "Update user info.")
  public ResultData updateUserInfo(@RequestBody User user) {
    ResultData resultData = userService.upgradeUserInfoByUser(user);
    return resultData;
  }
}
