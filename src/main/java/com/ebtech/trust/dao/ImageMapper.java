package com.ebtech.trust.dao;

import com.ebtech.trust.entity.Image;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 轮播图管理Mapper
 *
 * @author andcool
 * @date 2020/7/4 10:16 上午
 */
@Mapper
public interface ImageMapper {

  Image getImageById(@Param("id") Long id);

  List<Image> getCarouselImages();

  Long getImageAll();

  List<Image> getImageByPage(Long page, Long limit);

  void insetImage(Image image);

  void deleteImage(@Param("id") Long id);

  void updateImage(@Param("id") Long id, @Param("isCarousel") Integer isCarousel);

  void updateImageOrder(@Param("id") Long id, @Param("orders") Integer orders);

}
