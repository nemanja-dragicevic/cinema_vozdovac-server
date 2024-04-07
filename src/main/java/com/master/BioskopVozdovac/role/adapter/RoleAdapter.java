package com.master.BioskopVozdovac.role.adapter;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.movie.adapter.MovieAdapter;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleAdapter {

    private final ActorAdapter actorAdapter;

    private final MovieAdapter movieAdapter;


    public RoleDTO toDTO(final RoleEntity entity) {
        if (entity == null)
            return null;

        final RoleDTO dto = new RoleDTO();
        dto.setRoleID(entity.getRoleID());
        dto.setActor(actorAdapter.entityToDTO(entity.getActor()));
        dto.setMovie(movieAdapter.entityToDTO(entity.getMovie()));
        dto.setRoleName(entity.getRoleName());

        return dto;
    }

    public Set<RoleDTO> toDTOs(final Set<RoleEntity> list) {
        if(list == null)
            return null;

        return list.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public RoleEntity toEntity(final RoleDTO dto) {
        if (dto == null)
            return null;

        final RoleEntity entity = new RoleEntity();

        entity.setRoleID(dto.getRoleID());
        entity.setMovie(movieAdapter.dtoToEntity(dto.getMovie()));
        entity.setActor(actorAdapter.dtoToEntity(dto.getActor()));
        entity.setRoleName(dto.getRoleName());

        return entity;
    }

    public Set<RoleEntity> toEntities(Set<RoleDTO> roles) {
        if (roles == null)
            return null;

        return roles.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    public List<RoleDTO> toDTOs(final List<RoleEntity> entities) {
        if (entities == null)
            return null;

        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
