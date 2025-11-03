package com.konkuk.gongmojeon.api.member.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.konkuk.gongmojeon.domain.uploadFile.entity.UploadFile;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberLocalRegisterReq {

  @NotNull
  private String email;
  @NotNull
  private String password;

  private String name;

  @Setter
  @JsonIgnore
  private UploadFile profileImage;
  private Long profileImageId;
}
