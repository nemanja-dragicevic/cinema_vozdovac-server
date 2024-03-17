package com.master.BioskopVozdovac.movie.adapter;

import com.master.BioskopVozdovac.actor.adapter.ActorAdapter;
import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieAdapter {

    @Autowired
    private GenreAdapter genreAdapter;

    @Autowired
    private ActorAdapter actorAdapter;

    @Autowired
    private RoleAdapter roleAdapter;

    public MovieEntity dtoToEntity(final MovieDTO dto) {
        if (dto == null)
            return null;

        final MovieEntity entity = new MovieEntity();

        entity.setMovieID(dto.getMovieID());
        entity.setName(dto.getName());
        entity.setStartTime(dto.getStartTime());
        entity.setDescription(dto.getDescription());
        entity.setDuration(dto.getDuration());
        entity.setGenres(genreAdapter.toEntity(dto.getGenres()));
        entity.setRoles(roleAdapter.toEntities(dto.getRoles()));
        
        return entity;
    }

    public MovieDTO entityToDTO(final MovieEntity entity) {
        if (entity == null)
            return null;

        final MovieDTO dto = new MovieDTO();

        dto.setMovieID(entity.getMovieID());
        dto.setName(entity.getName());
        dto.setStartTime(entity.getStartTime());
        dto.setDescription(entity.getDescription());
        dto.setDuration(entity.getDuration());
        dto.setGenres(genreAdapter.toDto(entity.getGenres()));
        dto.setRoles(roleAdapter.toDTOs(entity.getRoles()));

        return dto;
    }

    public List<MovieDTO> toDto(List<MovieEntity> entities) {
        if (entities == null)
            return null;

        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
