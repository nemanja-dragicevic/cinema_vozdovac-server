package com.master.BioskopVozdovac.genre.repository;

import com.master.BioskopVozdovac.genre.model.GenreEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.master.BioskopVozdovac.input.GenreData.GENRE_ENTITY;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    private GenreEntity genreEntity;

    @BeforeEach
    void setUp() {
        genreEntity = save();
    }

    @Test
    void testFindById() {
        GenreEntity foundEntity = repository.findById(genreEntity.getGenreID()).orElse(null);

        assertNotNull(foundEntity);
        assertEquals(genreEntity.getGenreID(), foundEntity.getGenreID());
        assertEquals(genreEntity.getName(), foundEntity.getName());
    }

    @Test
    void testFindByIdNotFound() {
        repository.deleteById(genreEntity.getGenreID());
        Optional<GenreEntity> foundEntity = repository.findById(genreEntity.getGenreID());
        assertFalse(foundEntity.isPresent());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(genreEntity.getGenreID());
        Optional<GenreEntity> foundEntity = repository.findById(genreEntity.getGenreID());
        assertFalse(foundEntity.isPresent());
    }

    GenreEntity save() {
        return repository.save(GENRE_ENTITY);
    }

}
