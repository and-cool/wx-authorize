package com.ebtech.trust.service.impl;

import com.ebtech.trust.service.CmsService;
import com.ebtech.trust.dao.ImageMapper;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.Image;
import com.ebtech.trust.utils.UploadImageUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/2 10:17 上午
 */
@Service
public class CmsServiceImpl implements CmsService {

  Logger log = LoggerFactory.getLogger(CmsServiceImpl.class);

  @Value("${admin.account}")
  private String account;

  @Value("${admin.password}")
  private String password;

  private ImageMapper imageMapper;

  private UploadImageUtil uploadImageUtil;

  public CmsServiceImpl(ImageMapper imageMapper,
      UploadImageUtil uploadImageUtil) {
    this.imageMapper = imageMapper;
    this.uploadImageUtil = uploadImageUtil;
  }

  @Override
  public Boolean verifyUsernameAndPassword(String username, String password) {
    if (account.equals(username) && password.equals(password)) {
      return true;
    }
    return false;
  }

  @Override
  public ResultData uploadImage(MultipartFile file) {
    String imageSrc = uploadImageUtil.uploadSingleImage(file);

    Image image = Image.builder()
        .src(imageSrc)
        .createAt(new Date())
        .updateAt(new Date())
        .build();
    imageMapper.insetImage(image);
    return ResultData.isOk();
  }

  @Override
  public Map<String, Object> findImageByPage(Long page, Long limit) {
    Long size = imageMapper.getImageAll();
    List<Image> images = imageMapper.getImageByPage((page - 1) * limit, limit);
    Map<String, Object> map = new HashMap<>();
    map.put("code", 0);
    map.put("msg", "success");
    map.put("count", size);
    map.put("data", images);
    log.info(String.valueOf(map));
    return map;
  }

  @Override
  public ResultData deleteImage(Long id) {
    Image image = imageMapper.getImageById(id);
    Boolean result = uploadImageUtil.deleteImage(image.getSrc());
    if(result) {
      imageMapper.deleteImage(id);
      return ResultData.isOk();
    }
    return ResultData.isFail();
  }

  @Override
  public ResultData updateImageState(Long id, Integer isCarousel) {
    imageMapper.updateImage(id, isCarousel);
    return ResultData.isOk();
  }

  @Override
  public ResultData getCarouselImages() {
    List<Image> images = imageMapper.getCarouselImages();
    return new ResultData().isOk(images);
  }
}
