package com.example.wechatauthorize.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.wechatauthorize.config.WxConfig;
import com.example.wechatauthorize.dao.UserMapper;
import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.entity.User;
import com.example.wechatauthorize.service.UserService;
import com.example.wechatauthorize.utils.WXAppletUserInfo;
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

  private UserMapper userMapper;

  public UserServiceImpl(WxConfig wxConfig, UserMapper userMapper) {
    this.wxConfig = wxConfig;
    this.userMapper = userMapper;
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
  public ResultData saveUserInfoByEncryptedData(String encryptedData, String iv, String sessionKey) {
    // 通过加密数据解密算法获取用户详细信息
    JSONObject userInfo = WXAppletUserInfo.getUserInfo(encryptedData, iv, sessionKey);
    updateUserInfo(
        (String) userInfo.get("nickName"),
        (Integer) userInfo.get("gender"),
        (String) userInfo.get("country"),
        (String) userInfo.get("province"),
        (String) userInfo.get("city"),
        (String) userInfo.get("avatarUrl"),
        (String) userInfo.get("language"),
        (String) userInfo.get("openId"));
    return new ResultData().isOk(userInfo);
  }

  @Override
  public ResultData saveUserInfoByUser(User user) {

    updateUserInfo(
        user.getNickName(),
        user.getGender(),
        user.getCountry(),
        user.getProvince(),
        user.getCity(),
        user.getAvatarUrl(),
        user.getLanguage(),
        user.getOpenId()
    );
    return new ResultData().isOk(user);
  }

  public void updateUserInfo(String nickName, Integer gender, String country,
      String province, String city, String avatarUrl, String language, String openId) {
    User user = User.builder()
        .nickName(nickName)
        .gender(gender)
        .country(country)
        .province(province)
        .city(city)
        .avatarUrl(avatarUrl)
        .language(language)
        .openId(openId)
        .build();
    userMapper.updateUserInfo(user);
  }
}
