package com.ebtech.trust.dao;

import com.ebtech.trust.entity.Image;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author andcool
 * @date 2020/7/4 10:16 上午
 */
public interface ImageMapper {

  @Select("select * from `sys_image` where id = #{id}")
  Image getImageById(@Param("id") Long id);

  @Select("select * from `sys_image` where is_carousel = 1")
  List<Image> getCarouselImages();

  @Select("select count(*) from `sys_image`")
  Long getImageAll();

  @Select("select * from `sys_image` order by id desc limit #{page}, #{limit}")
  List<Image> getImageByPage(Long page, Long limit);

  @Insert("insert into `sys_image` (src, create_at, update_at) values(#{src}, #{createAt}, #{updateAt})")
  void insetImage(Image image);

  @Delete("delete from `sys_image` where id = #{id}")
  void deleteImage(@Param("id") Long id);

  @Update("update `sys_image` set is_carousel=#{isCarousel} where id=#{id}")
  void updateImage(@Param("id") Long id, @Param("isCarousel") Integer isCarousel);

}
