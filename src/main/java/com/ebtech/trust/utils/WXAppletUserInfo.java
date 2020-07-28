package com.ebtech.trust.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebtech.trust.config.WxConfig;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解密用户信息和手机号
 *
 * @author andcool
 * @date 2020/6/28 9:04 下午
 */
public class WXAppletUserInfo {

  private static Logger log = LoggerFactory.getLogger(WXAppletUserInfo.class);

  /**
   * 获取微信小程序 session_key 和 openid
   *
   * @param code 调用微信登陆返回的Code
   * @return
   * @author zhy
   */
  public static JSONObject getSessionKeyOrOpenId(String code, WxConfig wxConfig) {
    //微信端登录code值
    String wxCode = code;
    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
    Map<String, String> requestUrlParam = new HashMap<String, String>();
    requestUrlParam.put("appid", wxConfig.getAppId());  //开发者设置中的appId
    requestUrlParam.put("secret", wxConfig.getAppSecret());  //开发者设置中的appSecret
    requestUrlParam.put("js_code", wxCode);  //小程序调用wx.login返回的code
    requestUrlParam.put("grant_type", "authorization_code");  //默认参数
    log.info(requestUrlParam.toString());
    //发送get请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doGet(requestUrl, requestUrlParam));
    log.info(String.valueOf(jsonObject));
    return jsonObject;
  }

  public static JSONObject getUserInfo(String encryptedData, String iv, String sessionKey) {
    // 被加密的数据
    byte[] dataByte = Base64.decode(encryptedData);
    // 加密秘钥
    byte[] keyByte = Base64.decode(sessionKey);
    // 偏移量
    byte[] ivByte = Base64.decode(iv);
    try {
      // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
      int base = 16;
      if (keyByte.length % base != 0) {
        int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
        byte[] temp = new byte[groups * base];
        Arrays.fill(temp, (byte) 0);
        System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
        keyByte = temp;
      }
      // 初始化
      Security.addProvider(new BouncyCastleProvider());
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
      SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
      AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
      parameters.init(new IvParameterSpec(ivByte));
      cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
      byte[] resultByte = cipher.doFinal(dataByte);
      if (null != resultByte && resultByte.length > 0) {
        String result = new String(resultByte, "UTF-8");
        return JSON.parseObject(result);
      }
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
    } catch (NoSuchPaddingException e) {
      log.error(e.getMessage(), e);
    } catch (InvalidParameterSpecException e) {
      log.error(e.getMessage(), e);
    } catch (IllegalBlockSizeException e) {
      log.error(e.getMessage(), e);
    } catch (BadPaddingException e) {
      log.error(e.getMessage(), e);
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    } catch (InvalidKeyException e) {
      log.error(e.getMessage(), e);
    } catch (InvalidAlgorithmParameterException e) {
      log.error(e.getMessage(), e);
    } catch (NoSuchProviderException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }
}
