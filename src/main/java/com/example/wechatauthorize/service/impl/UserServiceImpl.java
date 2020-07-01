package com.example.wechatauthorize.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.wechatauthorize.config.SmsConfig;
import com.example.wechatauthorize.config.WxConfig;
import com.example.wechatauthorize.dao.UserMapper;
import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.entity.User;
import com.example.wechatauthorize.service.UserService;
import com.example.wechatauthorize.utils.GenerateCaptchaUtil;
import com.example.wechatauthorize.utils.RedisUtil;
import com.example.wechatauthorize.utils.SendMessageUtil;
import com.example.wechatauthorize.utils.WXAppletUserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author andcool
 * @date 2020/6/28 8:38 下午
 */
@Service
public class UserServiceImpl implements UserService {

  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private WxConfig wxConfig;

  private SmsConfig smsConfig;

  private UserMapper userMapper;

  private RedisUtil redisUtil;

  public UserServiceImpl(WxConfig wxConfig, SmsConfig smsConfig,
      UserMapper userMapper, RedisUtil redisUtil) {
    this.wxConfig = wxConfig;
    this.smsConfig = smsConfig;
    this.userMapper = userMapper;
    this.redisUtil = redisUtil;
  }

  @Override
  public ResultData getUserinfoByOpenId(String openId) {
    User user = userMapper.selectUserByOpenId(openId);
    return new ResultData().isOk(user);
  }

  @Override
  public ResultData getUserOpenIdByCode(String code) {

    JSONObject result = WXAppletUserInfo.getSessionKeyOrOpenId(code, wxConfig);
    if (result.get("errcode") != null) {
      return new ResultData(Integer.parseInt(result.get("errcode").toString()),
          (String) result.get("errmsg"));
    }
    //查询openid是否已存入数据库
    User user = userMapper.selectUserByOpenId((String) result.get("openid"));
    if (user != null) {
      //不等于空说明已经存入该openid的用户 返回前台登录成功 并存入userSession
      userMapper.updateSessionKeyByOpenId((String) result.get("openid"),
          (String) result.get("session_key"));
      return new ResultData().isOk(result);
    }
    //等于空说明已经不存在该用户，插入记录 并存入userSession，返回前台登录成功
    user = new User();
    user.setOpenId((String) result.get("openid"));
    user.setSessionKey((String) result.get("session_key"));
    userMapper.insertUser(user);
    return new ResultData().isOk(result);
  }

  @Override
  public ResultData saveUserInfoByEncryptedData(String encryptedData, String iv,
      String sessionKey, String openId) {
    // 通过加密数据解密算法获取用户详细信息
    JSONObject decryptedData = WXAppletUserInfo.getUserInfo(encryptedData, iv, sessionKey);
    // {"phoneNumber":"18610365819","watermark":{"appid":"wx3be21ad44d8869b5","timestamp":1593583549},"purePhoneNumber":"18610365819","countryCode":"86"}
    logger.info("userInfo ===== " + String.valueOf(decryptedData));
    logger.info((String) decryptedData.get("phoneNumber"));
    if ((String) decryptedData.get("phoneNumber") != null) {
      User buildUserPhoneNumber = User.builder()
          .phoneNumber((String) decryptedData.get("phoneNumber"))
          .purePhoneNumber((String) decryptedData.get("purePhoneNumber"))
          .countryCode((String) decryptedData.get("countryCode"))
          .openId(openId).build();
      userMapper.updateUserPhoneNumber(buildUserPhoneNumber);
    }
    if((String) decryptedData.get("openId") != null) {
      User buildUser = User.builder()
          .nickName((String) decryptedData.get("nickName"))
          .gender((Integer) decryptedData.get("gender"))
          .country((String) decryptedData.get("country"))
          .province((String) decryptedData.get("province"))
          .city((String) decryptedData.get("city"))
          .avatarUrl((String) decryptedData.get("avatarUrl"))
          .language((String) decryptedData.get("language"))
          .openId(openId)
          .build();
      userMapper.updateUserInfo(buildUser);
    }
    return new ResultData().isOk(decryptedData);
  }

  @Override
  public ResultData saveUserInfoByUser(User user) {
    User buildUser = User.builder()
        .nickName(user.getNickName())
        .gender(user.getGender())
        .country(user.getCountry())
        .province(user.getProvince())
        .city(user.getCity())
        .avatarUrl(user.getAvatarUrl())
        .language(user.getLanguage())
        .openId(user.getOpenId())
        .build();
    userMapper.updateUserInfo(buildUser);
    return new ResultData().isOk(buildUser);
  }

  @Override
  public ResultData sendPhoneCaptcha(String phone) {
    // generate captcha
    String captcha = GenerateCaptchaUtil.generateCaptcha();
    // List of sent SMS(phone and message)
    List<Map<String, Object>> sendList = new ArrayList<>();
    Map<String, Object> sendMap = new HashMap<>();
    sendMap.put("phone", phone);
    sendMap.put("message", "【光大信托】验证码为：" + captcha + "，请勿转发，本验证码5分钟有效；");
    sendList.add(sendMap);
    // Send messages
    Boolean result = SendMessageUtil.sendMsg(sendList, smsConfig);
    if (result) {
      // Store the mobile phone number and captcha in redis
      redisUtil.set(phone, captcha, 5 * 60);
    }
    return new ResultData().isOk(result);
  }

  @Override
  public ResultData verifyPhoneAndCaptcha(String phone, String captcha) {
    if (redisUtil.hasKey(phone)) {
      String value = (String) redisUtil.get(phone);
      if (value.equals(captcha)) {
        redisUtil.del(phone);
        return new ResultData().isOk();
      } else {
        return new ResultData().isFail("登录失败，请重试");
      }
    } else {
      return new ResultData().isFail("验证码已过期，请重新获取");
    }
  }

  @Override
  public ResultData upgradeUserInfoByUser(User user) {
    User buildUser = User.builder()
        .name(user.getName())
        .occupation((user.getOccupation()))
        .address(user.getAddress())
        .telephone(user.getTelephone())
        .email(user.getEmail())
        .isGroupCustomer(user.getIsGroupCustomer())
        .openId(user.getOpenId())
        .build();
    userMapper.updateUserOtherInfo(buildUser);

    User upgradeUser = userMapper.selectUserByOpenId(user.getOpenId());
    System.out.println(String.valueOf(upgradeUser));
    return new ResultData().isOk(upgradeUser);
  }
}
