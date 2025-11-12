package com.konkuk.refit.api.member.common.auth.facade;

import com.konkuk.refit.api.member.common.auth.dto.LocalLoginReq;
import com.konkuk.refit.api.member.common.auth.dto.MemberLocalRegisterReq;
import com.konkuk.refit.api.member.common.auth.dto.MemberWithTokenResp;
import com.konkuk.refit.api.member.common.auth.dto.RefreshReq;
import com.konkuk.refit.api.member.global.facade.MemberGlobalFacade;
import com.konkuk.refit.common.auth.jwt.Jwt;
import com.konkuk.refit.common.auth.jwt.Jwt.Claims;
import com.konkuk.refit.common.auth.jwt.TokenResponse;
import com.konkuk.refit.common.exception.BusinessException;
import com.konkuk.refit.common.exception.ErrorCode;
import com.konkuk.refit.domain.member.entity.Member;
import com.konkuk.refit.domain.member.service.MemberService;
import com.konkuk.refit.domain.uploadFile.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAuthFacade {

  private final MemberService memberService;
  private final Jwt jwt;
  private final PasswordEncoder passwordEncoder;
  private final MemberGlobalFacade memberGlobalFacade;
  private final UploadFileService uploadFileService;

  @Transactional
  public MemberWithTokenResp login(LocalLoginReq req) {
    try {
      Member member = memberService.getByEmail(req.getEmail());

      boolean matches = passwordEncoder.matches(req.getPassword(), member.getPassword());
      if (!matches) {
        throw new BusinessException(ErrorCode.LOGIN_FAILED);
      }

      TokenResponse tokenResponse = jwt.generateAllToken(
          Claims.from(member.getId()));

      return this.memberWithTokenResp(member, tokenResponse);
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.LOGIN_FAILED);
    }
  }

  public MemberWithTokenResp refresh(RefreshReq req) {
    try {

      Claims claims = jwt.verify(req.getRefreshToken());
      Long memberId = claims.getMemberId();
      Member member = memberService.getById(memberId);

      TokenResponse tokenResponse = jwt.generateAllToken(
          Claims.from(member.getId()));

      return this.memberWithTokenResp(member, tokenResponse);
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }
  }

  @Transactional
  public MemberWithTokenResp registerLocal(MemberLocalRegisterReq req) {

    if (req.getProfileImageId() != null) {
      req.setProfileImage(uploadFileService.getById(req.getProfileImageId()));
    }
    Member member = memberService.registerLocal(req);
    TokenResponse token = jwt.generateAllToken(
        Claims.from(member.getId()));
    return MemberWithTokenResp.builder()
        .member(memberGlobalFacade.memberResp(member))
        .token(token)
        .build();
  }



  public MemberWithTokenResp memberWithTokenResp(Member member, TokenResponse tokenResponse) {
    return MemberWithTokenResp.builder()
        .member(memberGlobalFacade.memberResp(member))
        .token(tokenResponse)
        .build();
  }


  public void logout(Long userId) {

  }
}
