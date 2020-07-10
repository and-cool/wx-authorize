package com.ebtech.trust.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author andcool
 * @date 2020/7/1 11:16 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CmsLoginRequest {

  private String username;
  private String password;
}
