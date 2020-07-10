package com.ebtech.trust.interceptor;

import com.ebtech.trust.utils.CookieUtils;
import com.ebtech.trust.config.CookieConfig;
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

  private CookieConfig cookieConfig;

  public CmsInterceptor(CookieConfig cookieConfig) {
    this.cookieConfig = cookieConfig;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String userToken = CookieUtils.getCookieValue(request, cookieConfig.getCookieName());
    String url = request.getRequestURL().toString();
    if (userToken == null) {
      response.sendRedirect("/cms/login?redirect=" + url);
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
