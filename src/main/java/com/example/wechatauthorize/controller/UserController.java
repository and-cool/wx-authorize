package com.example.wechatauthorize.controller;

import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.dto.SaveUserRequest;
import com.example.wechatauthorize.entity.User;
import com.example.wechatauthorize.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andcool
 * @date 2020/6/28 8:35 下午
 */
@RestController
@RequestMapping("/wx/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResultData getOpenId(@RequestBody String code) {
    ResultData resultData = userService.getUserOpenIdByCode(code);
    return resultData;
  }

  @PostMapping("/save/decrypt")
  public ResultData saveUserInfoByEncryptedData(@RequestBody SaveUserRequest saveUserRequest) {
    ResultData resultData = userService.saveUserInfoByEncryptedData(
        saveUserRequest.getEncryptedData(),
        saveUserRequest.getIv(),
        saveUserRequest.getSessionKey());
    return resultData;
  }

  @PostMapping("/save/info")
  public ResultData saveUserInfoByUser(@RequestBody User user) {
    ResultData resultData = userService.saveUserInfoByUser(user);
    return resultData;
  }
}
