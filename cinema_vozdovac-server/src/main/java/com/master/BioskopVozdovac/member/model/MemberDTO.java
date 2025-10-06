package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class MemberDTO {

    private Long memberID;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    private MemberRole role;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    private String password;

    private String token;

    private MemberDTO(Long memberID, String firstName, String lastName, LocalDate birthDate,
                         MemberRole role, Gender gender, String email, String username) {
        this.memberID = memberID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.gender = gender;
        this.email = email;
        this.username = username;
    }

    public static MemberDTO create(Long memberID, String firstName, String lastName, LocalDate birthDate,
                                      MemberRole role, Gender gender, String email, String username) {
        return new MemberDTO(memberID, firstName, lastName, birthDate, role, gender, email, username);
    }

}
