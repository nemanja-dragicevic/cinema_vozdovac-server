package com.master.BioskopVozdovac.genre.service;

import com.master.BioskopVozdovac.genre.adapter.GenreAdapter;
import com.master.BioskopVozdovac.genre.model.GenreDTO;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.genre.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreAdapter genreAdapter;

    @InjectMocks
    private GenreService genreService;

    private GenreEntity genreEntity;
    private GenreDTO genreDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        genreEntity = GenreEntity.builder().genreID(1L).name("Action").build();

        genreDTO = GenreDTO.builder().genreID(1L).name("Action").build();
    }

    @Test
    void testGetAllGenres() {
        when(genreRepository.findAll()).thenReturn(List.of(genreEntity));
        when(genreAdapter.toDto(anyList())).thenReturn(List.of(genreDTO));

        List<GenreDTO> genres = genreService.getAllGenres();

        assertNotNull(genres);
        assertEquals(1, genres.size());
        assertEquals("Action", genres.get(0).getName());
        verify(genreRepository, times(1)).findAll();
        verify(genreAdapter, times(1)).toDto(anyList());
    }

    @Test
    void testSaveGenre() {
        when(genreAdapter.dtoToEntity(any(GenreDTO.class))).thenReturn(genreEntity);
        when(genreRepository.saveAndFlush(any(GenreEntity.class))).thenReturn(genreEntity);

        GenreDTO saved = genreService.saveGenre(genreDTO);

        assertNotNull(saved);
        assertEquals(1, saved.getGenreID());
        assertEquals("Action", saved.getName());
        verify(genreRepository, times(1)).saveAndFlush(any(GenreEntity.class));
    }

    @Test
    void testDeleteGenre() {
        doNothing().when(genreRepository).deleteById(anyLong());

        String result = genreService.deleteGenre(1L);

        assertEquals("Successfully deleted genre", result);
        verify(genreRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testUpdateGenre() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genreEntity));
        when(genreAdapter.dtoToEntity(any(GenreDTO.class))).thenReturn(genreEntity);
        when(genreRepository.saveAndFlush(any(GenreEntity.class))).thenReturn(genreEntity);
        when(genreAdapter.entityToDTO(any(GenreEntity.class))).thenReturn(genreDTO);

        GenreDTO updated = genreService.updateGenre(genreDTO);

        assertNotNull(updated);
        assertEquals(1L, updated.getGenreID());
        assertEquals("Action", updated.getName());
        verify(genreRepository, times(1)).findById(anyLong());
        verify(genreRepository, times(1)).saveAndFlush(any(GenreEntity.class));
    }

    @Test
    void testUpdateGenreNotFound() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            genreService.updateGenre(genreDTO);
        });

        assertEquals("There is no element with id: 1", exception.getMessage());
        verify(genreRepository, times(1)).findById(anyLong());
    }

}
