package com.konkuk.refit.api.member.common.auth.dto;

import lombok.Getter;

@Getter
public class LocalLoginReq {

  private String email;
  private String password;

}
