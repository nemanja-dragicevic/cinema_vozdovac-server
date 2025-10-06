package com.master.BioskopVozdovac.genre.adapter;

import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.master.BioskopVozdovac.input.GenreData.GENRE_DTO;
import static com.master.BioskopVozdovac.input.GenreData.GENRE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenreAdapterTest {

    @Test
    void testEntityToDTO() {
        GenreDTO dto = GenreAdapter.entityToDTO(GENRE_ENTITY);

        assertNotNull(dto);
        assertEquals(GENRE_ENTITY.getGenreID(), dto.genreID());
        assertEquals(GENRE_ENTITY.getName(), dto.name());
    }

    @Test
    void testDTOToEntity() {
        GenreEntity entity = GenreAdapter.dtoToEntity(GENRE_DTO);
        assertNotNull(entity);
        assertEquals(GENRE_DTO.genreID(), entity.getGenreID());
        assertEquals(GENRE_DTO.name(), entity.getName());
    }

    @Test
    void testToDTO() {
        Set<GenreEntity> entities = Set.of(GENRE_ENTITY);

        Set<GenreDTO> dtos = GenreAdapter.toDto(entities);
        assertNotNull(dtos);
        assertEquals(entities.size(), dtos.size());
        assertEquals(GENRE_ENTITY.getGenreID(), dtos.iterator().next().genreID());
        assertEquals(GENRE_ENTITY.getName(), dtos.iterator().next().name());
    }

    @Test
    void testToEntity() {
        Set<GenreDTO> dtos = Set.of(GENRE_DTO);

        Set<GenreEntity> entities = GenreAdapter.toEntity(dtos);
        assertNotNull(entities);
        assertEquals(dtos.size(), entities.size());
        assertEquals(GENRE_DTO.genreID(), entities.iterator().next().getGenreID());
        assertEquals(GENRE_DTO.name(), entities.iterator().next().getName());
    }

}
