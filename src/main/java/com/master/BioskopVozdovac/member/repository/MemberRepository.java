package com.master.BioskopVozdovac.member.repository;

import com.master.BioskopVozdovac.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("select m from MemberEntity m where m.username = ?1")
    Optional<MemberEntity> findByUsername(String username);
}
