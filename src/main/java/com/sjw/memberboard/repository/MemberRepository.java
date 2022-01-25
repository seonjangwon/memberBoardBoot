package com.sjw.memberboard.repository;

import com.sjw.memberboard.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
