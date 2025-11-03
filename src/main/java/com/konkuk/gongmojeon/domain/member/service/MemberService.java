package com.konkuk.gongmojeon.domain.member.service;

import com.konkuk.gongmojeon.api.member.common.auth.dto.MemberLocalRegisterReq;
import com.konkuk.gongmojeon.common.exception.BusinessException;
import com.konkuk.gongmojeon.common.exception.ErrorCode;
import com.konkuk.gongmojeon.domain.member.entity.Member;
import com.konkuk.gongmojeon.domain.member.entity.dto.MemberResp;
import com.konkuk.gongmojeon.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public Member getById(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "Member"));
  }

  public Member getByEmail(String email) {
    return memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "Member"));
  }

  public Optional<Member> findById(Long memberId) {
    return memberRepository.findById(memberId);
  }

  public List<Member> gets(Set<Long> memberIds) {
    return memberRepository.findAllById(memberIds);
  }


  @Transactional
  public Member registerLocal(MemberLocalRegisterReq req) {
    String email = req.getEmail();

    if (memberRepository.findByEmail(email).isPresent()) {
      throw new BusinessException(ErrorCode.EMAIL_DUPLICATED);
    }
    String encodedPassword = passwordEncoder.encode(req.getPassword());
    return memberRepository.save(
        Member.builder()
            .email(email)
            .password(encodedPassword)
            .profileImage(req.getProfileImage())
            .name(req.getName())
            .isFirstLogin(false)
            .build()
    );
  }
}
