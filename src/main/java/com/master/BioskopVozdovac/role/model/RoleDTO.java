package com.master.BioskopVozdovac.role.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long roleID;

    private Long actorID;

    private Long movieID;

    private String roleName;

}
