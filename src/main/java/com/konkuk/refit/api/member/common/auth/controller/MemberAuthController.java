package com.konkuk.refit.api.member.common.auth.controller;

import static com.konkuk.refit.common.util.AuthUtil.getUserId;

import com.konkuk.refit.api.member.common.auth.dto.LocalLoginReq;
import com.konkuk.refit.api.member.common.auth.dto.MemberLocalRegisterReq;
import com.konkuk.refit.api.member.common.auth.dto.MemberWithTokenResp;
import com.konkuk.refit.api.member.common.auth.dto.RefreshReq;
import com.konkuk.refit.api.member.common.auth.facade.MemberAuthFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/auth")
@RequiredArgsConstructor
@Tag(name = "[회원/공용] 로그인/인증")
public class MemberAuthController {

  private final MemberAuthFacade memberAuthFacade;

  @Operation(summary = "이메일 회원가입")
  @PostMapping("/register/local")
  public MemberWithTokenResp register(
      @RequestBody MemberLocalRegisterReq req
  ) {
    return memberAuthFacade.registerLocal(req);
  }

  @Operation(summary = "이메일 로그인")
  @PostMapping("/login/local")
  public MemberWithTokenResp login(
      @RequestBody LocalLoginReq req
  ) {
    return memberAuthFacade.login(req);
  }
  @Operation(summary = "토큰 갱신")
  @PostMapping("/refresh")
  public MemberWithTokenResp refresh(
      @RequestBody RefreshReq req
  ) {
    return memberAuthFacade.refresh(req);
  }

  @Operation(summary = "로그아웃")
  @GetMapping("/logout")
  public void logout(
      HttpServletRequest request
  ) {
    memberAuthFacade.logout(getUserId());
  }

//  @Operation(summary = "임시 비밀번호 메일 전송")
//  @GetMapping("/send-reset-password-mail")
//  public ResetResp sendResetPasswordMail(
//      @RequestParam String email
//  ) {
//    return memberAuthFacade.sendResetPasswordMail(email);
//  }
//
//  @Operation(summary = "이메일 중복 검사")
//  @GetMapping("/validate/email")
//  public MemberEmailCheckResp checkEmail(
//      @RequestParam("email") String email
//  ) {
//    return memberAuthFacade.checkEmail(email);
//  }
}
