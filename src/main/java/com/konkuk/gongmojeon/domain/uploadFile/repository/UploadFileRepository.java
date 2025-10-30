package com.konkuk.gongmojeon.domain.uploadFile.repository;

import com.konkuk.gongmojeon.domain.uploadFile.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

}
