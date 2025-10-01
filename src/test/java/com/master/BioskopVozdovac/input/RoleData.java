package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;

import static com.master.BioskopVozdovac.input.ActorData.ACTOR_ENTITY;
import static com.master.BioskopVozdovac.input.MovieData.MOVIE_ENTITY;

public class RoleData {

    public static final RoleDTO ROLE_DTO = RoleDTO.builder()
            .roleID(1L)
            .actorID(1L)
            .movieID(1L)
            .roleName("Driver")
            .build();

    public static final RoleEntity ROLE_ENTITY = RoleEntity.create(1L, ACTOR_ENTITY, MOVIE_ENTITY, "Driver");

}
