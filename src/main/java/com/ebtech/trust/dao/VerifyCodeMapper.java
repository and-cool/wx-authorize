package com.ebtech.trust.dao;

import com.ebtech.trust.entity.VerifyCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author andcool
 * @date 2020/7/20 11:24 上午
 */
public interface VerifyCodeMapper {

  @Select("select * from `t_verify_code` where phone = #{phone}")
  VerifyCode selectVerifyCodeByPhone(@Param("phone") String phone);

  @Insert("insert into `t_verify_code` (phone, code, create_at, expire) values(#{phone}, #{code}, now(), #{expire})")
  void insertVerifyCode(VerifyCode verifyCode);

  @Update("update `t_verify_code` set code = #{code}, create_at = now(), expire = #{expire} where phone = #{phone}")
  void updateVerifyCode(VerifyCode verifyCode);

  @Delete("delete from `t_verify_code` where phone = #{phone}")
  void deleteVerifyCode(@Param("phone") String phone);

}
