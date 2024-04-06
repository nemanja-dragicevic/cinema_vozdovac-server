package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long memberID;

    private String firstName;

    private String lastName;

    private Gender gender;

    private MemberRole role;

    private LocalDate birthDate;

    private String email;

    private String username;

    private String password;

    private String token;

}
