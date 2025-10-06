package com.master.BioskopVozdovac.movie.adapter;

import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class MovieAdapter {

    private final RoleAdapter roleAdapter;

    public MovieEntity dtoToEntity(final MovieDTO dto) {
        if (dto == null)
            return null;

        final MovieEntity entity = new MovieEntity();

        entity.setMovieID(dto.getMovieID());
        entity.setName(dto.getName());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDescription(dto.getDescription());
        entity.setDuration(dto.getDuration());
        entity.setGenres(GenreAdapter.toEntity(dto.getGenres()));
        
        return entity;
    }

    public MovieDTO entityToDTO(final MovieEntity entity) {
        if (entity == null)
            return null;

        final MovieDTO dto = new MovieDTO();

        dto.setMovieID(entity.getMovieID());
        dto.setName(entity.getName());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setDescription(entity.getDescription());
        dto.setDuration(entity.getDuration());
        dto.setGenres(GenreAdapter.toDto(entity.getGenres()));
        dto.setRoleDTO(roleAdapter.toDTOs(entity.getRoles()));

        return dto;
    }

    public List<MovieDTO> toDto(List<MovieEntity> entities) {
        if (entities == null)
            return Collections.emptyList();

        return entities.stream().map(this::entityToDTO).toList();
    }

}
