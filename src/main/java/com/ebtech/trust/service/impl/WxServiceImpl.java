package com.ebtech.trust.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ebtech.trust.config.SmsConfig;
import com.ebtech.trust.config.WxConfig;
import com.ebtech.trust.dao.WxUserMapper;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.WxUser;
import com.ebtech.trust.service.WxService;
import com.ebtech.trust.utils.GenerateCaptchaUtil;
import com.ebtech.trust.utils.SendMessageUtil;
import com.ebtech.trust.utils.VerifyCodeUtils;
import com.ebtech.trust.utils.WXAppletUserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信用户信息管理
 *
 * @author andcool
 * @date 2020/6/28 8:38 下午
 */
@Service
public class WxServiceImpl implements WxService {

  private Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

  @Value("${verify.code.max-age}")
  private Integer verifyCodeMaxAge;

  private WxConfig wxConfig;

  private SmsConfig smsConfig;

  private WxUserMapper wxUserMapper;

  private VerifyCodeUtils verifyCodeUtils;

  public WxServiceImpl(WxConfig wxConfig, SmsConfig smsConfig,
      WxUserMapper wxUserMapper, VerifyCodeUtils verifyCodeUtils) {
    this.wxConfig = wxConfig;
    this.smsConfig = smsConfig;
    this.wxUserMapper = wxUserMapper;
    this.verifyCodeUtils = verifyCodeUtils;
  }

  @Override
  public ResultData getUserinfoByOpenId(String openId) {
    WxUser wxUser = wxUserMapper.selectUserByOpenId(openId);
    wxUser.setPhoneNumber(formatPhoneNumber(wxUser.getPhoneNumber()));
    return new ResultData().isOk(wxUser);
  }

  @Override
  public ResultData getUserOpenIdByCode(String code) {
    JSONObject result = WXAppletUserInfo.getSessionKeyOrOpenId(code, wxConfig);
    if (result.get("errcode") != null) {
      return new ResultData(Integer.parseInt(result.get("errcode").toString()),
          (String) result.get("errmsg"));
    }
    //查询openid是否已存入数据库
    WxUser wxUser = wxUserMapper.selectUserByOpenId((String) result.get("openid"));
    if (wxUser != null) {
      //不等于空说明已经存入该openid的用户 返回前台登录成功 并存入userSession
      wxUserMapper.updateSessionKeyByOpenId((String) result.get("openid"),
          (String) result.get("session_key"));
      return new ResultData().isOk(result);
    }
    //等于空说明已经不存在该用户，插入记录 并存入userSession，返回前台登录成功
    wxUser = new WxUser();
    wxUser.setOpenId((String) result.get("openid"));
    wxUser.setSessionKey((String) result.get("session_key"));
    wxUserMapper.insertUser(wxUser);
    return new ResultData().isOk(result);
  }

  @Override
  public ResultData saveUserInfoByEncryptedData(String encryptedData, String iv,
      String sessionKey, String openId) {
    // 通过加密数据解密算法获取用户详细信息
    JSONObject decryptedData = WXAppletUserInfo.getUserInfo(encryptedData, iv, sessionKey);
    // {"phoneNumber":"18610365819","watermark":{"appid":"wx3be21ad44d8869b5","timestamp":1593583549},"purePhoneNumber":"18610365819","countryCode":"86"}
    if (null == decryptedData) {
      return new ResultData().isFail("数据解析失败");
    }
    if (null != (String) decryptedData.get("phoneNumber")) {
      WxUser buildWxUserPhoneNumber = WxUser.builder()
          .phoneNumber((String) decryptedData.get("phoneNumber"))
          .purePhoneNumber((String) decryptedData.get("purePhoneNumber"))
          .countryCode((String) decryptedData.get("countryCode"))
          .openId(openId).build();
      wxUserMapper.updateUserPhoneNumber(buildWxUserPhoneNumber);
      WxUser wxUser = wxUserMapper.selectUserByOpenId(openId);
      wxUser.setPhoneNumber(formatPhoneNumber(wxUser.getPhoneNumber()));
      return new ResultData().isOk(wxUser);
    }
    if (null != (String) decryptedData.get("openId")) {
      WxUser buildWxUser = WxUser.builder()
          .nickName((String) decryptedData.get("nickName"))
          .gender((Integer) decryptedData.get("gender"))
          .country((String) decryptedData.get("country"))
          .province((String) decryptedData.get("province"))
          .city((String) decryptedData.get("city"))
          .avatarUrl((String) decryptedData.get("avatarUrl"))
          .language((String) decryptedData.get("language"))
          .openId(openId)
          .build();
      wxUserMapper.updateUserInfo(buildWxUser);
      WxUser wxUser = wxUserMapper.selectUserByOpenId(openId);
      wxUser.setPhoneNumber(formatPhoneNumber(wxUser.getPhoneNumber()));
      return new ResultData().isOk(wxUser);
    }
    return new ResultData().isFail("数据保存失败");
  }

  @Override
  public ResultData saveUserInfoByUser(WxUser wxUser) {
    WxUser buildWxUser = WxUser.builder()
        .nickName(wxUser.getNickName())
        .gender(wxUser.getGender())
        .country(wxUser.getCountry())
        .province(wxUser.getProvince())
        .city(wxUser.getCity())
        .avatarUrl(wxUser.getAvatarUrl())
        .language(wxUser.getLanguage())
        .openId(wxUser.getOpenId())
        .build();
    wxUserMapper.updateUserInfo(buildWxUser);
    WxUser wxUserInfo = wxUserMapper.selectUserByOpenId(wxUser.getOpenId());
    wxUserInfo.setPhoneNumber(formatPhoneNumber(wxUserInfo.getPhoneNumber()));
    return new ResultData().isOk(wxUserInfo);
  }

  @Override
  public ResultData sendPhoneCaptcha(String phone) {
    // generate captcha
    String captcha = GenerateCaptchaUtil.generateCaptcha();
    // List of sent SMS(phone and message)
    List<Map<String, Object>> sendList = new ArrayList<>();
    Map<String, Object> sendMap = new HashMap<>();
    sendMap.put("phone", phone);
    sendMap.put("message", "验证码为：" + captcha + "，请勿转发，本验证码5分钟有效；");
    sendList.add(sendMap);
    // Send messages
    Boolean result = SendMessageUtil.sendMsg(sendList, smsConfig);
    if (result) {
      // Store the mobile phone number and captcha in redis
      verifyCodeUtils.set(phone, captcha, verifyCodeMaxAge);
    }
    return new ResultData().isOk(result);
  }

  @Override
  public ResultData verifyPhoneAndCaptcha(String phone, String captcha, String openId) {
    logger.info(phone + " : " + captcha);
    if (verifyCodeUtils.hasKey(phone)) {
      String value = (String) verifyCodeUtils.get(phone);
      if (value.equals(captcha)) {
        WxUser wxUser = wxUserMapper.selectUserByOpenId(openId);
        wxUser.setPhoneNumber(phone);
        wxUser.setPurePhoneNumber(phone);
        wxUserMapper.updateUserPhoneNumber(wxUser);
        verifyCodeUtils.del(phone);
        wxUser.setPhoneNumber(formatPhoneNumber(wxUser.getPhoneNumber()));
        return new ResultData().isOk(wxUser);
      } else {
        return new ResultData().isFail("请输入正确的验证码");
      }
    } else {
      return new ResultData().isFail("验证码已过期，请重新获取");
    }
  }

  @Override
  public ResultData upgradeUserInfoByUser(WxUser wxUser) {
    WxUser buildWxUser = WxUser.builder()
        .name(wxUser.getName())
        .occupation((wxUser.getOccupation()))
        .address(wxUser.getAddress())
        .telephone(wxUser.getTelephone())
        .email(wxUser.getEmail())
        .isGroupCustomer(wxUser.getIsGroupCustomer())
        .openId(wxUser.getOpenId())
        .build();
    wxUserMapper.updateUserOtherInfo(buildWxUser);

    WxUser upgradeWxUser = wxUserMapper.selectUserByOpenId(wxUser.getOpenId());
    System.out.println(String.valueOf(upgradeWxUser));
    return new ResultData().isOk(upgradeWxUser);
  }

  public String formatPhoneNumber(String phone) {
    try{
      return phone.substring(0,3) + "****" + phone.substring(7);
    }catch (Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
}
