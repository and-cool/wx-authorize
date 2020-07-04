package com.example.wechatauthorize.utils;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author andcool
 * @date 2020/7/4 10:03 上午
 */
public class UploadImageUtil {

  Logger log = LoggerFactory.getLogger(UploadImageUtil.class);

  public String uploadSingleImage(MultipartFile file) {
    String rootDir = this.getClass().getClassLoader().getResource("").getFile();
    File fileDir = new File(rootDir + "/images");
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
    return "/images/" + fileName;
  }

  public Boolean deleteImage(String src) {
    String rootDir = this.getClass().getClassLoader().getResource("").getFile();
    File file = new File(rootDir + src);
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
