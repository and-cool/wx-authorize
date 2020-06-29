package com.example.wechatauthorize.service;

import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.entity.User;

/**
 * @author andcool
 * @date 2020/6/28 8:38 下午
 */
public interface UserService {

  ResultData getUserOpenIdByCode(String code);

  ResultData saveUserInfoByEncryptedData(String encryptedData, String iv, String sessionKey);

  ResultData saveUserInfoByUser(User user);

}
