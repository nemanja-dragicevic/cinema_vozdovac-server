package com.master.BioskopVozdovac.role.adapter;

import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleAdapter {

    private final MovieRepository movieRepository;

    private final ActorRepository actorRepository;

    public RoleDTO toDTO(final RoleEntity entity) {
        if (entity == null)
            return null;

        final RoleDTO dto = new RoleDTO();
        dto.setRoleID(entity.getRoleID());
        dto.setMovieID(prepareMovie(entity.getMovie()));
        dto.setActorID(prepareActor(entity.getActor()));
        dto.setRoleName(entity.getRoleName());

        return dto;
    }

    private Long prepareActor(ActorEntity actor) {
        if (actor == null)
            return null;
        return actor.getActorID();
    }

    private Long prepareMovie(MovieEntity movie) {
        if (movie == null)
            return null;
        return movie.getMovieID();
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
        entity.setMovie(prepareMovie(dto.getMovieID()));
        entity.setActor(prepareActor(dto.getActorID()));
        entity.setRoleName(dto.getRoleName());

        return entity;
    }

    private MovieEntity prepareMovie(final Long id) {
        if (id == null)
            return null;
        return movieRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Couldn't find movie with id: " + id));
    }

    private ActorEntity prepareActor(final Long id) {
        if (id == null)
            return null;
        return actorRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Couldn't find actor with id: " + id));
    }

    public Set<RoleEntity> toEntities(Set<RoleDTO> roles) {
        if (roles == null)
            return null;

        return roles.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
