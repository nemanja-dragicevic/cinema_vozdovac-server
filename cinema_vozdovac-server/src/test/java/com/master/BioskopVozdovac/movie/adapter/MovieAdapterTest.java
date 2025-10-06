package com.master.BioskopVozdovac.movie.adapter;

import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.master.BioskopVozdovac.input.MovieData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieAdapterTest {

    @InjectMocks
    private MovieAdapter movieAdapter;

    @Mock
    private RoleAdapter roleAdapter;

    @Test
    void testDTOToEntity() {
        MovieEntity entity = movieAdapter.dtoToEntity(MOVIE_DTO);

        assertNotNull(entity);
        assertEquals(MOVIE_DTO.getName(), entity.getName());
        assertEquals(MOVIE_DTO.getDescription(), entity.getDescription());
        assertEquals(MOVIE_DTO.getDuration(), entity.getDuration());
    }

    @Test
    void testDTOToEntityNull() {
        MovieEntity entity = movieAdapter.dtoToEntity(null);

        assertNull(entity);
    }

    @Test
    void testEntityToDTO() {
        when(roleAdapter.toDTOs(anySet())).thenReturn(Set.of(ROLE_DTO));

        MovieDTO dto = movieAdapter.entityToDTO(MOVIE_ENTITY);

        assertNotNull(dto);
        assertEquals(MOVIE_ENTITY.getName(), dto.getName());
        assertEquals(MOVIE_ENTITY.getDescription(), dto.getDescription());
        assertEquals(MOVIE_ENTITY.getDuration(), dto.getDuration());
    }

}
