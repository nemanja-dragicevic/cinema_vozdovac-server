package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import com.master.BioskopVozdovac.member.model.MemberEntity;

import java.time.LocalDate;

public class MemberEntityData {

    public static final MemberEntity MEMBER_ENTITY = MemberEntity.builder()
            .memberID(1L)
            .firstName("John")
            .lastName("Wick")
            .birthDate(LocalDate.of(1980, 9, 1)) // Assuming John Wick was born on September 1, 1980
            .role(MemberRole.USER)
            .gender(Gender.MALE)
            .email("john.wick@example.com")
            .username("john_wick")
            .password("password123")
            .build();

}
