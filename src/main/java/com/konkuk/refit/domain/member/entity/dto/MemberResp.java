package com.konkuk.refit.domain.member.entity.dto;

import com.konkuk.refit.common.entity.dto.resp.BaseResp;
import com.konkuk.refit.domain.uploadFile.entity.dto.resp.UploadFileResp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResp extends BaseResp {

  private String email;
  private String name;
  private boolean isFirstLogin;
  private UploadFileResp profileImage;


}
