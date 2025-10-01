package com.master.BioskopVozdovac.movie.adapter;

import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import org.junit.jupiter.api.Test;

import static com.master.BioskopVozdovac.input.MovieData.MOVIE_DTO;
import static com.master.BioskopVozdovac.input.MovieData.MOVIE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieAdapterTest {

    @Test
    void testDTOToEntity() {
        MovieEntity entity = MovieAdapter.dtoToEntity(MOVIE_DTO);

        assertNotNull(entity);
        assertEquals(MOVIE_DTO.getName(), entity.getName());
        assertEquals(MOVIE_DTO.getDescription(), entity.getDescription());
        assertEquals(MOVIE_DTO.getDuration(), entity.getDuration());
    }

    @Test
    void testEntityToDTO() {
        MovieDTO dto = MovieAdapter.entityToDTO(MOVIE_ENTITY);

        assertNotNull(dto);
        assertEquals(MOVIE_ENTITY.getName(), dto.getName());
        assertEquals(MOVIE_ENTITY.getDescription(), dto.getDescription());
        assertEquals(MOVIE_ENTITY.getDuration(), dto.getDuration());
    }

}
