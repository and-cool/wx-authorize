package com.example.wechatauthorize.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/7/1 9:33 上午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetOpenIdRequest {

  private String code;
}
