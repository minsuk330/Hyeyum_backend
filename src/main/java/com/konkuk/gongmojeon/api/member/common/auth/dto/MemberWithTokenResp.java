package com.konkuk.gongmojeon.api.member.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.konkuk.gongmojeon.common.auth.jwt.TokenResponse;
import com.konkuk.gongmojeon.domain.member.entity.dto.MemberResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
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
