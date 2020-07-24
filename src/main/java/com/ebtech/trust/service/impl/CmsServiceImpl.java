package com.ebtech.trust.service.impl;

import com.ebtech.trust.config.SmsConfig;
import com.ebtech.trust.dao.CmsAdminMapper;
import com.ebtech.trust.entity.CmsAdmin;
import com.ebtech.trust.service.CmsService;
import com.ebtech.trust.dao.ImageMapper;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.Image;
import com.ebtech.trust.utils.EncryptPassword;
import com.ebtech.trust.utils.GenerateCaptchaUtil;
import com.ebtech.trust.utils.SendMessageUtil;
import com.ebtech.trust.utils.UploadImageUtil;
import com.ebtech.trust.utils.VerifyCodeUtils;
import java.util.ArrayList;
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

  @Value("${verify.code.max-age}")
  private Integer verifyCodeMaxAge;

  @Value("${admin.phone.prefix}")
  private String adminPhonePrefix;

  private ImageMapper imageMapper;

  private CmsAdminMapper cmsAdminMapper;

  private UploadImageUtil uploadImageUtil;

  private SmsConfig smsConfig;

  private VerifyCodeUtils redisUtil;

  public CmsServiceImpl(ImageMapper imageMapper,
      CmsAdminMapper cmsAdminMapper, UploadImageUtil uploadImageUtil,
      SmsConfig smsConfig, VerifyCodeUtils redisUtil) {
    this.imageMapper = imageMapper;
    this.cmsAdminMapper = cmsAdminMapper;
    this.uploadImageUtil = uploadImageUtil;
    this.smsConfig = smsConfig;
    this.redisUtil = redisUtil;
  }

  @Override
  public ResultData createCmsAdmin(CmsAdmin cmsAdmin) {
    cmsAdmin.setPassword(EncryptPassword.encode(cmsAdmin.getPassword()));
    cmsAdminMapper.createCmsAdmin(cmsAdmin);
    return new ResultData().isOk(cmsAdmin);
  }

  @Override
  public ResultData verifyUsernameAndPassword(String username, String password) {
    CmsAdmin cmsAdmin = cmsAdminMapper.selectCmsAdminByUsername(username);
    if(null != cmsAdmin) {
      boolean matches = EncryptPassword.matches(password, cmsAdmin.getPassword());
      if(matches) {
        return new ResultData().isOk(cmsAdmin);
      }else{
        return ResultData.isFail("账号或密码错误");
      }
    }
    return ResultData.isFail("账号或密码错误");
  }

  @Override
  public ResultData sendPhoneCaptcha(String phone) {
    // generate captcha
    String captcha = GenerateCaptchaUtil.generateCaptcha();
    // List of sent SMS(phone and message)
    List<Map<String, Object>> sendList = new ArrayList<>();
    Map<String, Object> sendMap = new HashMap<>();
    sendMap.put("phone", phone);
    sendMap.put("message", "验证码为：" + captcha + "，请勿转发，本验证码5分钟有效；");
    sendList.add(sendMap);
    // Send messages
    Boolean result = SendMessageUtil.sendMsg(sendList, smsConfig);
    if (result) {
      // Store the mobile phone number and captcha in redis
      redisUtil.set(adminPhonePrefix + phone, captcha, verifyCodeMaxAge);
    }
    return new ResultData().isOk(result);
  }

  @Override
  public ResultData updatePassword(String phone, String code, String password) {
    // request: phone, code, password
    CmsAdmin cmsAdmin = cmsAdminMapper.selectCmsAdminByPhone(phone);
    if(null == cmsAdmin) {
      return ResultData.isFail("查询无该账户");
    }
    if(redisUtil.hasKey(adminPhonePrefix + cmsAdmin.getPhone())) {
      String codeSave = (String) redisUtil.get(adminPhonePrefix + cmsAdmin.getPhone());
      if(codeSave.equals(code)) {
        cmsAdmin.setPassword(EncryptPassword.encode(password));
        cmsAdminMapper.updateCmsAdminPassword(cmsAdmin.getPassword(), cmsAdmin.getPhone());
        redisUtil.del(adminPhonePrefix + cmsAdmin.getPhone());
        return new ResultData().isOk("密码修改成功");
      }else{
        return new ResultData().isFail("验证码错误");
      }
    }else{
      return new ResultData().isFail("验证码已过期，请重新获取");
    }
  }

  @Override
  public ResultData updatePhone(String newPhone, String code, String oldPhone) {
    CmsAdmin cmsAdmin = cmsAdminMapper.selectCmsAdminByPhone(oldPhone);
    if(null == cmsAdmin) {
      return ResultData.isFail("查询无该账户");
    }
    if(redisUtil.hasKey(adminPhonePrefix + newPhone)) {
      String saveCode = (String) redisUtil.get(adminPhonePrefix + newPhone);
      if(saveCode.equals(code)) {
        cmsAdmin.setPhone(newPhone);
        cmsAdminMapper.updateCmsAdminPhone(newPhone, oldPhone);
        redisUtil.del(adminPhonePrefix + newPhone);
        return new ResultData().isOk("手机号修改成功");
      }else{
        return new ResultData().isFail("验证码错误");
      }
    }else{
      return new ResultData().isFail("验证码已过期，请重新获取");
    }
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
    if(null == image) {
      return ResultData.isFail("无效的照片");
    }
    imageMapper.deleteImage(id);
    Boolean result = uploadImageUtil.deleteImage(image.getSrc());
    return new ResultData().isOk("删除成功");
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
