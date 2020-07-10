package com.ebtech.trust.dao;

import com.ebtech.trust.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author andcool
 * @date 2020/6/29 9:33 上午
 */
public interface UserMapper {

  /**
   * openid查user
   */
  @Select("select * from `user` where open_id = #{openid}")
  User selectUserByOpenId(@Param("openid") String openid);

  /**
   * 新用户插入
   */
  @Insert("insert into user(open_id, session_key, create_at, update_at) values(#{openId}, #{sessionKey}, now(), now())")
  void insertUser(User user);

  /**
   * updateSessionKeyByOpenId
   */
  @Update("update user set session_key = #{sessionKey} where open_id = #{openid}")
  void updateSessionKeyByOpenId(@Param("openid") String openid,
      @Param("sessionKey") String sessionKey);

  /**
   * updateUserInfo
   *
   * @param user 用户信息
   */
  @Update("update user set nick_name = #{nickName}, gender = #{gender}, country = #{country}, province = #{province}, city = #{city}, avatar_url = #{avatarUrl}, language = #{language}, update_at = now() where open_id = #{openId}")
  void updateUserInfo(User user);

  /**
   * updateUserphoneNumber（解密手机号加密信息）
   *
   * @param user 用户信息
   */
  @Update("update user set phone_number = #{phoneNumber}, pure_phone_number = #{purePhoneNumber}, country_code = #{countryCode}, update_at = now() where open_id = #{openId}")
  void updateUserPhoneNumber(User user);

  /**
   * updateUserOtherInfo
   *
   * @param user 用户信息
   */
  @Update("update user set name = #{name}, occupation = #{occupation}, address = #{address}, telephone = #{telephone}, email = #{email}, is_group_customer = #{isGroupCustomer}, update_at = now() where open_id = #{openId}")
  void updateUserOtherInfo(User user);
}
