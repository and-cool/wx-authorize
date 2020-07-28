package com.ebtech.trust.dao;

import com.ebtech.trust.entity.VerifyCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 验证码Mapper
 *
 * @author andcool
 * @date 2020/7/20 11:24 上午
 */
@Mapper
public interface VerifyCodeMapper {

  VerifyCode selectVerifyCodeByPhone(@Param("phone") String phone);

  void insertVerifyCode(VerifyCode verifyCode);

  void updateVerifyCode(VerifyCode verifyCode);

  void deleteVerifyCode(@Param("phone") String phone);

}
