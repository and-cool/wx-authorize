package com.ebtech.trust.utils;

import com.ebtech.trust.dao.VerifyCodeMapper;
import com.ebtech.trust.entity.VerifyCode;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * 核销验证码处理
 *
 * @author andcool
 * @date 2020/7/20 11:38 上午
 */
@Component
public class VerifyCodeUtils {

  private VerifyCodeMapper verifyCodeMapper;

  public VerifyCodeUtils(VerifyCodeMapper verifyCodeMapper) {
    this.verifyCodeMapper = verifyCodeMapper;
  }


  /**
   * 判断key是否存在
   *
   * @param key 键
   * @return true 存在 false不存在
   */
  public boolean hasKey(String key) {
    try {
      VerifyCode verifyCode = verifyCodeMapper.selectVerifyCodeByPhone(key);
      System.out.println(String.valueOf(verifyCode));
      System.out.println(verifyCode.getCreateAt());
      System.out.println();
      Date expireTime = new Date(verifyCode.getCreateAt().getTime() + verifyCode.getExpire()*1000);
      // expireTime 的时间戳 晚于 当前时间(expireTime的时间戳大于当前的时间戳)
      return expireTime.after(new Date()) ? true : false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存放入并设置当前时间
   *
   * @param key   键
   * @param value 值
   * @param time  时间(秒) time要大于0
   * @return true成功 false 失败
   */
  public boolean set(String key, Object value, long time) {
    try {
      if (time <= 0) {
        return false;
      }
      VerifyCode verifyCode = verifyCodeMapper.selectVerifyCodeByPhone(key);
      if(null != verifyCode) {
        verifyCode.setCode((String) value);
        verifyCode.setExpire(time);
        verifyCodeMapper.updateVerifyCode(verifyCode);
      }else{
        VerifyCode verifyCodeBuild = VerifyCode.builder()
            .phone(key)
            .code((String) value)
            .expire(time)
            .build();
        verifyCodeMapper.insertVerifyCode(verifyCodeBuild);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object get(String key) {
    return key == null ? null : verifyCodeMapper.selectVerifyCodeByPhone(key).getCode();
  }

  /**
   * 删除缓存
   *
   * @param key 可以传一个值
   */
  public void del(String key) {
    verifyCodeMapper.deleteVerifyCode(key);
  }

}
