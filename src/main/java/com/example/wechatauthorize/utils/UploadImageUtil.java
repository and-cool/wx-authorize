package com.example.wechatauthorize.utils;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/4 10:03 上午
 */
@Component
public class UploadImageUtil {

  Logger log = LoggerFactory.getLogger(UploadImageUtil.class);

  @Value("${file.upload.path}")
  private String path;

  @Value("${file.upload.path.relative}")
  private String relativePath;

  public String uploadSingleImage(MultipartFile file) {
    File fileDir = new File(relativePath + path);
    if (!fileDir.exists()) {
      fileDir.mkdir();
    }
    String originalFilename = file.getOriginalFilename();
    String prefix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    StringBuffer fileName = new StringBuffer();
    fileName.append(System.currentTimeMillis()).append(".").append(prefix);
    try {
      file.transferTo(new File(fileDir, fileName.toString()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return path + "/" + fileName;
  }

  public Boolean deleteImage(String src) {
    File file = new File(relativePath + src);
    if (file.exists()) {
      if(file.delete()) {
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }
}
