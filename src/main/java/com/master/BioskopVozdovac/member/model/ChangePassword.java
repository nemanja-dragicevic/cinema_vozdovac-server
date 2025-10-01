package com.master.BioskopVozdovac.member.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

    @NotNull
    private MemberDTO member;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}
