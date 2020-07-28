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
@Api(tags = "内容管理系统接口")
public class CmsController {

  private CmsService cmsService;

  private SetCookieConfig cookieConfig;

  public CmsController(CmsService cmsService,
      SetCookieConfig cookieConfig) {
    this.cmsService = cmsService;
    this.cookieConfig = cookieConfig;
  }

  /**
   * 页面视图接口.
   *
   * @param mv ModalAndView视图.
   * @param pageName 页面名称.
   * @param request  request.
   * @return ModelAndView.
   */
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

  /**
   * 创建新管理员用户.
   *
   * @param cmsAdmin 管理员实体
   * @return 200.
   */
  @PostMapping(value = "/admin/create")
  @ResponseBody
  public ResultData insert(@RequestBody CmsAdmin cmsAdmin) {
    return cmsService.createCmsAdmin(cmsAdmin);
  }

  /**
   *  后台管理系统登录接口
   *
   * @param username 用户名
   * @param password 密码
   * @param request  request
   * @param response response
   * @return 200.
   */
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

  /**
   * 获取验证码.
   *
   * @param phone 手机号
   * @return 200.
   */
  @GetMapping(value = "/phone/captcha")
  @ResponseBody
  public ResultData sendPhoneCaptcha(@RequestParam String phone) {
    return cmsService.sendPhoneCaptcha(phone);
  }

  /**
   * 后台管理系统修改密码
   *
   * @param phone 手机号
   * @param code  验证码
   * @param password 新密码
   * @return 200
   */
  @PostMapping(value = "/update/password")
  @ResponseBody
  public ResultData updatePassword(String phone, String code, String password) {
    return cmsService.updatePassword(phone, code, password);
  }

  /**
   * 后台管理系统修改手机号
   *
   * @param newPhone 新手机号
   * @param code 验证码
   * @param oldPhone 旧手机号
   * @return 200
   */
  @PostMapping(value = "/update/phone")
  @ResponseBody
  public ResultData updatePhone(String newPhone, String code, String oldPhone) {
    return cmsService.updatePhone(newPhone, code, oldPhone);
  }

  /**
   * 后台管理系统退出登录
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return 200.
   */
  @PostMapping(value = "/logout")
  @ResponseBody
  public ResultData logout(HttpServletRequest request, HttpServletResponse response) {
    String token = cookieConfig.getCookieName();
    CookieUtils.deleteCookie(request, response, token);
    return new ResultData().isOk("logout success");
  }

  /**
   * 上传图片接口
   *
   * @param file MultipartFile.
   * @return 200.
   */
  @PostMapping(value = "/fileUpload")
  @ResponseBody
  public ResultData fileUpload(@RequestParam(value = "file") MultipartFile file) {
    return cmsService.uploadImage(file);
  }

  /**
   * 为小程序端提供获取轮播图的接口
   *
   * @return imageList
   */
  @GetMapping(value = "/image/carousel")
  @ResponseBody
  public ResultData getImagesWithCarousel() {
    return cmsService.getCarouselImages();
  }

  /**
   * 后台管理系统获取所有图片.
   *
   * @param page  页数
   * @param limit 每页显示的条数
   * @return imageList
   */
  @GetMapping(value = "/images")
  @ResponseBody
  public Map<String, Object> findImageByPage(@RequestParam Long page, @RequestParam Long limit) {
    return cmsService.findImageByPage(page, limit);
  }

  /**
   * 删除图片
   *
   * @param id 图片id
   * @return 200
   */
  @DeleteMapping(value = "/image")
  @ResponseBody
  public ResultData deleteImage(@RequestParam Long id) {
    return cmsService.deleteImage(id);
  }

  /**
   * 修改图片状态 - 是否作为轮播图
   * @param id 图片id
   * @param isCarousel 是否作为轮播图 1-是 0-否
   * @return 200
   */
  @PatchMapping(value = "/image")
  @ResponseBody
  public ResultData updateImage(@RequestParam Long id, @RequestParam Integer isCarousel) {
    return cmsService.updateImageState(id, isCarousel);
  }

  /**
   * 修改轮播图的显示顺序
   *
   * @param id 图片id
   * @param orders 排序序号
   * @return 200
   */
  @PatchMapping(value = "/image/orders")
  @ResponseBody
  public ResultData updateImageOrders(@RequestParam Long id, @RequestParam Integer orders) {
    return cmsService.updateImageOrders(id, orders);
  }
}
