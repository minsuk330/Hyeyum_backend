package com.konkuk.gongmojeon.domain.uploadFile.entity.dto.resp;

import com.konkuk.gongmojeon.common.entity.dto.resp.BaseResp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResp extends BaseResp {

  private String fileName;
  private String fileUrl;
}
