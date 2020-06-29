package com.example.wechatauthorize.dao;

import com.example.wechatauthorize.entity.User;
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
  @Select("select a.* from `user` a where open_id = #{openid}")
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
   * @param user 用户信息
   */
  @Update("Update user set nick_name = #{nickName}, gender = #{gender}, country = #{country}, province = #{province}, city = #{city}, avatar_url = #{avatarUrl}, language = #{language}, update_at = now() where open_id = #{openId}")
  void updateUserInfo(User user);
}
