package com.master.BioskopVozdovac.genre.adapter;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreAdapter {

    public GenreDTO entityToDTO(final GenreEntity entity) {
        if (entity == null)
            return null;

        final GenreDTO dto = new GenreDTO();

        dto.setGenreID(entity.getGenreID());
        dto.setName(entity.getName());

        return dto;
    }

    public GenreEntity dtoToEntity(final GenreDTO dto) {
        if (dto == null)
            return null;

        final GenreEntity entity = new GenreEntity();

        entity.setGenreID(dto.getGenreID());
        entity.setName(dto.getName());

        return entity;
    }

    public Set<GenreDTO> toDto(final Set<GenreEntity> entities) {
        if (Objects.isNull(entities))
            return null;

        return entities.stream().map(this::entityToDTO).collect(Collectors.toSet());
    }

    public List<GenreDTO> toDto(final List<GenreEntity> entities) {
        if (Objects.isNull(entities))
            return null;

        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public Set<GenreEntity> toEntity(final Set<GenreDTO> dtos) {
        if (Objects.isNull(dtos))
            return null;

        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toSet());
    }

}
