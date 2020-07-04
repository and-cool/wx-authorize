package com.example.wechatauthorize.controller;

import com.example.wechatauthorize.config.CookieConfig;
import com.example.wechatauthorize.dto.ResultData;
import com.example.wechatauthorize.service.CmsService;
import com.example.wechatauthorize.utils.CookieUtils;
import io.swagger.annotations.Api;
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

  private CookieConfig cookieConfig;

  public CmsController(CmsService cmsService,
      CookieConfig cookieConfig) {
    this.cmsService = cmsService;
    this.cookieConfig = cookieConfig;
  }

  @RequestMapping(value = "/{pageName}")
  public ModelAndView login(ModelAndView mv, @PathVariable String pageName,
      HttpServletRequest request) {
    String userToken = CookieUtils.getCookieValue(request, cookieConfig.getCookieName());
    if ("login".equals(pageName) && userToken != null) {
      pageName = "homepage";
    }
    mv.setViewName(pageName);
    return mv;
  }

  @PostMapping(value = "/login",
      produces = {"application/json;charset=UTF-8"},
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseBody
  public ResultData login(String username, String password,
      HttpServletRequest request, HttpServletResponse response) {
    Boolean verifyResult = cmsService.verifyUsernameAndPassword(username, password);
    if (verifyResult) {
      String token = cookieConfig.getCookieName();
      CookieUtils.setCookie(request, response, cookieConfig.getCookieName(), token,
          cookieConfig.getMaxAge());
      return new ResultData().isOk("login success");
    } else {
      return new ResultData().isFail("login fail");
    }
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
