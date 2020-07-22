package com.ebtech.trust.dao;

import com.ebtech.trust.entity.CmsAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author andcool
 * @date 2020/7/21 3:05 下午
 */
public interface CmsAdminMapper {

  @Insert("insert into cms_admin(username, password, phone) values(#{username}, #{password}, #{phone})")
  void createCmsAdmin(CmsAdmin cmsAdmin);

  @Select("select * from `cms_admin` where username = #{username}")
  CmsAdmin selectCmsAdminByUsername(@Param("username") String username);

  @Select("select * from `cms_admin` where phone = #{phone}")
  CmsAdmin selectCmsAdminByPhone(@Param("phone") String phone);

  @Update("update cms_admin set password = #{password} where phone = #{phone}")
  void updateCmsAdminPassword(@Param("password") String password, @Param("phone") String phone);

  @Update("update cms_admin set phone = #{newPhone} where phone = #{oldPhone}")
  void updateCmsAdminPhone(@Param("newPhone") String newPhone, @Param("oldPhone") String oldPhone);

  @Update("update cms_admin set code = #{code} where phone = #{phone}")
  void updateCmsAdminCode(@Param("code") String code, @Param("phone") String phone);
}
