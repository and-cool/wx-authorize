package com.ebtech.trust.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 轮播图实体
 *
 * @author andcool
 * @date 2020/7/4 10:14 上午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {

  private Long id;

  private String src;

  private int isCarousel;

  private int orders;

  private Date createAt;

  private Date updateAt;

}
