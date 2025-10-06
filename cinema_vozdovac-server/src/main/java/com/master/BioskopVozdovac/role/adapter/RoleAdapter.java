package com.master.BioskopVozdovac.role.adapter;

import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleAdapter {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public RoleDTO toDTO(final RoleEntity entity) {
        if (entity == null)
            return null;

        return new RoleDTO(
                entity.getRoleID(),
                entity.getActor().getActorID(),
                entity.getMovie().getMovieID(),
                entity.getRoleName());
    }

    public Set<RoleDTO> toDTOs(final Set<RoleEntity> list) {
        if(list == null)
            return Set.of();

        return list.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public RoleEntity toEntity(final RoleDTO dto) {
        if (dto == null)
            return null;

        final RoleEntity entity = new RoleEntity();

        entity.setRoleID(dto.roleID());
//        entity.setMovie(movieRepository.findById(dto.movieID()).orElse(null));
        entity.setActor(actorRepository.findById(dto.actorID()).orElse(null));
        entity.setRoleName(dto.roleName());

        return entity;
    }

    public Set<RoleEntity> toEntities(Set<RoleDTO> roles) {
        if (roles == null)
            return Set.of();

        return roles.stream().map(this::toEntity).collect(Collectors.toSet());
    }

}
