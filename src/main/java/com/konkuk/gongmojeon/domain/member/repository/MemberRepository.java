package com.konkuk.gongmojeon.domain.member.repository;

import com.konkuk.gongmojeon.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,Long> {

  Optional<Member> findByEmail(String email);

  @Query("select count(m) > 0 from Member m where m.email = :email and m.deletedAt IS NULL")
  boolean existsByEmail(@Param("email")String email);

}
