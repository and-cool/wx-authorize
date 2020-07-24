package com.ebtech.trust.service;

import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.CmsAdmin;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/2 10:17 上午
 */
public interface CmsService {

  ResultData createCmsAdmin(CmsAdmin cmsAdmin);

  ResultData verifyUsernameAndPassword(String username, String password);

  ResultData sendPhoneCaptcha(String phone);

  ResultData updatePassword(String phone, String code, String password);

  ResultData updatePhone(String newPhone, String code, String oldPhone);

  ResultData uploadImage(MultipartFile file);

  Map<String, Object> findImageByPage(Long page, Long limit);

  ResultData deleteImage(Long id);

  ResultData updateImageState(Long id, Integer isCarousel);

  ResultData getCarouselImages();
}
