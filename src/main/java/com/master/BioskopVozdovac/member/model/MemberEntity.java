package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import com.master.BioskopVozdovac.user.User;
import com.master.BioskopVozdovac.util.PatternUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="members")
@SuperBuilder(toBuilder = true)
public class MemberEntity extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long memberID;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;

    @NotEmpty
    @Pattern(regexp = PatternUtils.USERNAME_PATTERN)
    private String username;

    @NotEmpty
    @Pattern(regexp = PatternUtils.PASSWORD_PATTERN)
    private String password;

}
