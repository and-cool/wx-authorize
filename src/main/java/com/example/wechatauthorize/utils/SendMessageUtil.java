package com.example.wechatauthorize.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.wechatauthorize.config.SmsConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author andcool
 * @date 2020/6/30 2:22 下午
 */
public class SendMessageUtil {

  private static final Logger log = LoggerFactory.getLogger(SendMessageUtil.class);

  private static final String sendMsg = "/smsservice/json/SendSMS";

  public static Boolean sendMsg(List<Map<String, Object>> sendList, SmsConfig smsConfig) {
    // 拼接发送短信参数
    // 时间戳
    Long timestamp = System.currentTimeMillis();

    // 签名算法： MD5(userId + MD5(password) + timestamp)
    String pwd = DigestUtils.md5DigestAsHex(smsConfig.getSmsPassword().getBytes());
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(smsConfig.getSmsUserId()).append(pwd).append(timestamp);
    String sign = DigestUtils.md5DigestAsHex(stringBuffer.toString().getBytes());

    // 构造请求参数
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userId", smsConfig.getSmsUserId());
    paramMap.put("sign", sign);
    paramMap.put("timestamp", timestamp);
    paramMap.put("messageList", sendList);
    // 设置Content-Type
    HttpHeaders requestHeaders = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    requestHeaders.setContentType(type);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(paramMap, requestHeaders);

    String url = smsConfig.getSmsUrl() + sendMsg;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<JSONObject> responseEntity = restTemplate
        .postForEntity(url, request, JSONObject.class);
    log.info(String.valueOf(responseEntity));
    if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
      // 请求状态为200
      String status = responseEntity.getBody().getString("status");
      if ("R01".equals(status)) {
        return true;
      } else {
        log.error("发送失败，状态码为:[{}]", status);
        return false;
      }
    } else {
      log.error("发送失败。返回码为:[{}]", responseEntity.getStatusCodeValue());
      return false;
    }
  }
}
