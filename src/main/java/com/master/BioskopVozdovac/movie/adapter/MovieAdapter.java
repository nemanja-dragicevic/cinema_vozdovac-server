package com.master.BioskopVozdovac.movie.adapter;

import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;

import java.util.Collections;
import java.util.List;

public final class MovieAdapter {

    private MovieAdapter(){
        throw new AssertionError();
    }

    public static MovieEntity dtoToEntity(final MovieDTO dto) {
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

    public static MovieDTO entityToDTO(final MovieEntity entity) {
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

        return dto;
    }

    public static List<MovieDTO> toDto(List<MovieEntity> entities) {
        if (entities == null)
            return Collections.emptyList();

        return entities.stream().map(MovieAdapter::entityToDTO).toList();
    }

}
