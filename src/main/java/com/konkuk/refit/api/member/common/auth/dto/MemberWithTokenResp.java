package com.konkuk.refit.api.member.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.konkuk.refit.common.auth.jwt.TokenResponse;
import com.konkuk.refit.domain.member.entity.dto.MemberResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberWithTokenResp {
  @JsonUnwrapped
  private MemberResp member;
  private TokenResponse token;
}
