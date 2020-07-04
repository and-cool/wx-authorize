package com.example.wechatauthorize.service.impl;

import com.example.wechatauthorize.dao.ImageMapper;
import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.entity.Image;
import com.example.wechatauthorize.service.CmsService;
import com.example.wechatauthorize.utils.UploadImageUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/2 10:17 上午
 */
@Service
public class CmsServiceImpl implements CmsService {

  Logger log = LoggerFactory.getLogger(CmsServiceImpl.class);

  private ImageMapper imageMapper;

  public CmsServiceImpl(ImageMapper imageMapper) {
    this.imageMapper = imageMapper;
  }

  @Override
  public Boolean verifyUsernameAndPassword(String username, String password) {
    if ("admin".equals(username) && "admin".equals(password)) {
      return true;
    }
    return false;
  }

  @Override
  public ResultData uploadImage(MultipartFile file) {
    String imageSrc = new UploadImageUtil().uploadSingleImage(file);

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
//    imageMapper.deleteImage(id);
//    return ResultData.isOk();

    Image image = imageMapper.getImageById(id);
    Boolean result = new UploadImageUtil().deleteImage(image.getSrc());
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
