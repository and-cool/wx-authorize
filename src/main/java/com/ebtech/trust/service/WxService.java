package com.ebtech.trust.service;

import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.WxUser;

/**
 * @author andcool
 * @date 2020/6/28 8:38 下午
 */
public interface WxService {

  ResultData getUserinfoByOpenId(String openId);

  ResultData getUserOpenIdByCode(String code);

  ResultData saveUserInfoByEncryptedData(String encryptedData, String iv, String sessionKey,
      String openId);

  ResultData saveUserInfoByUser(WxUser wxUser);

  ResultData sendPhoneCaptcha(String phone);

  ResultData verifyPhoneAndCaptcha(String phone, String captcha, String openId);

  ResultData upgradeUserInfoByUser(WxUser wxUser);

}
