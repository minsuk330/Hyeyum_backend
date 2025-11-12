package com.konkuk.refit.domain.uploadFile.entity;

import com.konkuk.refit.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "upload_file")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//presigned url로 s3에 업로드해서 사용할 예정
public class UploadFile extends BaseEntity {

  private String fileName;
  @Column(length = 2000)
  private String fileUrl;
}
