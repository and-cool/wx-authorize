package com.ebtech.trust.interceptor;

import com.ebtech.trust.utils.CookieUtils;
import com.ebtech.trust.config.SetCookieConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author andcool
 * @date 2020/7/2 9:52 上午
 */
@Component
public class CmsInterceptor implements HandlerInterceptor {

  private SetCookieConfig cookieConfig;

  public CmsInterceptor(SetCookieConfig cookieConfig) {
    this.cookieConfig = cookieConfig;
  }

  /**
   * 对后台管理系统的请求做拦截
   *
   * @param request request
   * @param response response
   * @param handler handler
   * @return true
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String userToken = CookieUtils.getCookieValue(request, cookieConfig.getCookieName());
    String url = request.getRequestURL().toString();
    if (userToken == "" || userToken == null) {
      response.sendRedirect("/cms/login?redirect=" + url);
      return false;
    } else {
      CookieUtils.setCookie(request, response, cookieConfig.getCookieName(), userToken,
          cookieConfig.getMaxAge());
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

  }
}
