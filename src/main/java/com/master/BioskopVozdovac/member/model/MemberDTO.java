package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberStatus;
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

    private String address;

    private MemberStatus status;

    private String phoneNumber;

    private LocalDate birthDate;

}
