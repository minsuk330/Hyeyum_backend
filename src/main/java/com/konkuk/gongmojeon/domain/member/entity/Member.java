package com.konkuk.gongmojeon.domain.member.entity;

import com.konkuk.gongmojeon.common.entity.BaseEntity;
import com.konkuk.gongmojeon.common.entity.vo.GenderType;
import com.konkuk.gongmojeon.domain.uploadFile.entity.UploadFile;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table
public class Member extends BaseEntity {

  private String email;
  private String password;

  @JoinColumn(name = "profile_image_file_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private UploadFile profileImage;

  private boolean isFirstLogin;

  private GenderType gender;

}
