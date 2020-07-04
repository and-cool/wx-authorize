package com.example.wechatauthorize.service;

import com.example.wechatauthorize.dto.ResultData;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/2 10:17 上午
 */
public interface CmsService {

  Boolean verifyUsernameAndPassword(String username, String password);

  ResultData uploadImage(MultipartFile file);

  Map<String, Object> findImageByPage(Long page, Long limit);

  ResultData deleteImage(Long id);

  ResultData updateImageState(Long id, Integer isCarousel);

  ResultData getCarouselImages();
}
