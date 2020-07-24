package com.ebtech.trust.controller;

import com.ebtech.trust.config.SetCookieConfig;
import com.ebtech.trust.dto.ResultData;
import com.ebtech.trust.entity.CmsAdmin;
import com.ebtech.trust.service.CmsService;
import com.ebtech.trust.utils.CookieUtils;
import io.swagger.annotations.Api;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author andcool
 * @date 2020/7/1 5:47 下午
 */
@Controller
@RequestMapping("/cms")
@Api(tags = "content management system operator interface")
public class CmsController {

  private CmsService cmsService;

  private SetCookieConfig cookieConfig;

  public CmsController(CmsService cmsService,
      SetCookieConfig cookieConfig) {
    this.cmsService = cmsService;
    this.cookieConfig = cookieConfig;
  }

  @RequestMapping(value = "/{pageName}")
  public ModelAndView login(ModelAndView mv, @PathVariable String pageName,
      HttpServletRequest request) {
    String userToken = CookieUtils.getCookieValue(request, cookieConfig.getCookieName());
    if ("login".equals(pageName) && userToken != null && userToken != "") {
      pageName = "homepage";
    }
    mv.setViewName(pageName);
    return mv;
  }

  @PostMapping(value = "/admin/create")
  @ResponseBody
  public ResultData insert(@RequestBody CmsAdmin cmsAdmin) {
    return cmsService.createCmsAdmin(cmsAdmin);
  }

  @PostMapping(value = "/login",
      produces = {"application/json;charset=UTF-8"},
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResultData login(String username, String password,
      HttpServletRequest request, HttpServletResponse response) {
    ResultData resultData = cmsService.verifyUsernameAndPassword(username, password);
    if (resultData.getCode() == 200) {
      CookieUtils.setCookie(request, response, cookieConfig.getCookieName(), String.valueOf(new Date().getTime()),
          cookieConfig.getMaxAge());
    }
    return resultData;
  }

  @GetMapping(value = "/phone/captcha")
  @ResponseBody
  public ResultData sendPhoneCaptcha(@RequestParam String phone) {
    return cmsService.sendPhoneCaptcha(phone);
  }

  @PostMapping(value = "/update/password")
  @ResponseBody
  public ResultData updatePassword(String phone, String code, String password) {
    return cmsService.updatePassword(phone, code, password);
  }

  @PostMapping(value = "/update/phone")
  @ResponseBody
  public ResultData updatePhone(String newPhone, String code, String oldPhone) {
    return cmsService.updatePhone(newPhone, code, oldPhone);
  }

  @PostMapping(value = "/logout")
  @ResponseBody
  public ResultData logout(HttpServletRequest request, HttpServletResponse response) {
    String token = cookieConfig.getCookieName();
    CookieUtils.deleteCookie(request, response, token);
    return new ResultData().isOk("logout success");
  }

  @PostMapping(value = "/fileUpload")
  @ResponseBody
  public ResultData fileUpload(@RequestParam(value = "file") MultipartFile file) {
    ResultData resultData = cmsService.uploadImage(file);
    return resultData;
  }

  @GetMapping(value = "/image/carousel")
  @ResponseBody
  public ResultData getImagesWithCarousel() {
    ResultData resultData = cmsService.getCarouselImages();
    System.out.println(resultData.getData());
    return resultData;
  }

  @GetMapping(value = "/images")
  @ResponseBody
  public Map<String, Object> findImageByPage(@RequestParam Long page, @RequestParam Long limit) {
    Map<String, Object> images = cmsService.findImageByPage(page, limit);
    return images;
  }

  @DeleteMapping(value = "/image")
  @ResponseBody
  public ResultData deleteImage(@RequestParam Long id) {
    ResultData resultData = cmsService.deleteImage(id);
    return resultData;
  }

  @PatchMapping(value = "/image")
  @ResponseBody
  public ResultData updateImage(@RequestParam Long id, @RequestParam Integer isCarousel) {
    ResultData resultData = cmsService.updateImageState(id, isCarousel);
    return resultData;
  }
}
