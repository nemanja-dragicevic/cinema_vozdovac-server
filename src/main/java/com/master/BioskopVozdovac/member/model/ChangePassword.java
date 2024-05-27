package com.master.BioskopVozdovac.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

    private MemberDTO member;

    private String oldPassword;

    private String newPassword;

}
