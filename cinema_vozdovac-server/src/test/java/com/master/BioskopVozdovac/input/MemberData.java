package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import com.master.BioskopVozdovac.member.model.MemberDTO;
import com.master.BioskopVozdovac.member.model.MemberEntity;

import java.time.LocalDate;

public class MemberData {

    public static final MemberEntity MEMBER_ENTITY = MemberEntity.builder()
            .memberID(0L)
            .firstName("John")
            .lastName("Wick")
            .birthDate(LocalDate.of(1980, 9, 1)) // Assuming John Wick was born on September 1, 1980
            .role(MemberRole.USER)
            .gender(Gender.MALE)
            .email("john.wick@example.com")
            .username("john_wick")
            .password("password123")
            .build();

    public static final MemberDTO MEMBER_DTO = MemberDTO.builder()
            .memberID(0L)
            .firstName("John")
            .lastName("Wick")
            .birthDate(LocalDate.of(1980, 9, 1)) // Assuming John Wick was born on September 1, 1980
            .role(MemberRole.USER)
            .gender(Gender.MALE)
            .email("john.wick@example.com")
            .username("john_wick")
            .password("password123")
            .build();

    public static final MemberDTO NO_PASS_MEMBER_DTO = MemberDTO.builder()
            .memberID(0L)
            .firstName("John")
            .lastName("Wick")
            .birthDate(LocalDate.of(1980, 9, 1)) // Assuming John Wick was born on September 1, 1980
            .role(MemberRole.USER)
            .gender(Gender.MALE)
            .email("john.wick@example.com")
            .username("john_wick")
            .build();

    public static final MemberEntity UPDATED_MEMBER_ENTITY = MEMBER_ENTITY.toBuilder()
            .firstName("Jackie")
            .lastName("Chan")
            .build();

    public static final MemberDTO UPDATED_MEMBER_DTO = MEMBER_DTO.toBuilder()
            .firstName("Jackie")
            .lastName("Chan")
            .build();

}
