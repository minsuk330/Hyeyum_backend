package com.konkuk.refit.api.member.global.facade;

import com.konkuk.refit.domain.member.entity.Member;
import com.konkuk.refit.domain.member.entity.dto.MemberResp;
import com.konkuk.refit.domain.member.service.MemberService;
import com.konkuk.refit.domain.uploadFile.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGlobalFacade {
  private final MemberService memberService;
  private final UploadFileService uploadFileService;

  public MemberResp memberResp(Member member) {
    return MemberResp.builder()
        .id(member.getId())
        .createdAt(member.getCreatedAt())
        .updatedAt(member.getUpdatedAt())
        .email(member.getEmail())
        .name(member.getName())
        .profileImage(uploadFileService.uploadFileResp(member.getProfileImage()))
        .build();
  }

}
