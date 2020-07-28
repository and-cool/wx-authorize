package com.ebtech.trust.dao;

import com.ebtech.trust.entity.CmsAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 后台管理系统Mapper
 *
 * @author andcool
 * @date 2020/7/21 3:05 下午
 */
@Mapper
public interface CmsAdminMapper {

  void createCmsAdmin(CmsAdmin cmsAdmin);

  CmsAdmin selectCmsAdminByUsername(@Param("username") String username);

  CmsAdmin selectCmsAdminByPhone(@Param("phone") String phone);

  void updateCmsAdminPassword(@Param("password") String password, @Param("phone") String phone);

  void updateCmsAdminPhone(@Param("newPhone") String newPhone, @Param("oldPhone") String oldPhone);

  void updateCmsAdminCode(@Param("code") String code, @Param("phone") String phone);
}
