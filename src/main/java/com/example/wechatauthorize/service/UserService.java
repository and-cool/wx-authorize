package com.example.wechatauthorize.service;

import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.entity.User;

/**
 * @author andcool
 * @date 2020/6/28 8:38 下午
 */
public interface UserService {

  ResultData getUserinfoByOpenId(String openId);

  ResultData getUserOpenIdByCode(String code);

  ResultData saveUserInfoByEncryptedData(String encryptedData, String iv, String sessionKey, String openId);

  ResultData saveUserInfoByUser(User user);

  ResultData sendPhoneCaptcha(String phone);

  ResultData verifyPhoneAndCaptcha(String phone, String captcha);

  ResultData upgradeUserInfoByUser(User user);

}
