package com.ebtech.trust.dao;

import com.ebtech.trust.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 微信用户管理Mapper
 *
 * @author andcool
 * @date 2020/6/29 9:33 上午
 */
@Mapper
public interface WxUserMapper {

  /**
   * openid查user
   */
  WxUser selectUserByOpenId(@Param("openid") String openid);

  /**
   * 新用户插入
   */
  void insertUser(WxUser wxUser);

  /**
   * updateSessionKeyByOpenId
   */
  void updateSessionKeyByOpenId(@Param("openid") String openid,
      @Param("sessionKey") String sessionKey);

  /**
   * updateUserInfo
   *
   * @param wxUser 用户信息
   */
  void updateUserInfo(WxUser wxUser);

  /**
   * updateUserphoneNumber（解密手机号加密信息）
   *
   * @param wxUser 用户信息
   */
  void updateUserPhoneNumber(WxUser wxUser);

  /**
   * updateUserOtherInfo
   *
   * @param wxUser 用户信息
   */
  void updateUserOtherInfo(WxUser wxUser);
}
