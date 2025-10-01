package com.master.BioskopVozdovac.genre.adapter;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class GenreAdapter {

    private GenreAdapter() {
        throw new AssertionError();
    }

    public static GenreDTO entityToDTO(final GenreEntity entity) {
        if (entity == null)
            return null;

        return new GenreDTO(entity.getGenreID(), entity.getName());
    }

    public static GenreEntity dtoToEntity(final GenreDTO dto) {
        if (dto == null)
            return null;

        return GenreEntity.create(dto.genreID(), dto.name());
    }

    public static Set<GenreDTO> toDto(final Set<GenreEntity> entities) {
        if (Objects.isNull(entities))
            return Set.of();

        return entities.stream().map(GenreAdapter::entityToDTO).collect(Collectors.toSet());
    }

    public static List<GenreDTO> toDto(final List<GenreEntity> entities) {
        if (Objects.isNull(entities))
            return List.of();

        return entities.stream().map(GenreAdapter::entityToDTO).toList();
    }

    public static Set<GenreEntity> toEntity(final Set<GenreDTO> dtos) {
        if (Objects.isNull(dtos))
            return Set.of();

        return dtos.stream().map(GenreAdapter::dtoToEntity).collect(Collectors.toSet());
    }

}
