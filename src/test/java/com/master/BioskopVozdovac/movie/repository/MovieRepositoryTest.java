package com.master.BioskopVozdovac.movie.repository;

import com.master.BioskopVozdovac.movie.model.MovieEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    private MovieEntity entity;
    private MovieEntity woProjections;

    @BeforeEach
    void setUp() {
        entity = MovieEntity.create(2L, "Transporter 1",
                "Professional driver", 120,
                LocalDate.now(), LocalDate.now().plusDays(1));
        woProjections = MovieEntity.create(3L, "Transporter 2",
                "Better than 1st", 120, LocalDate.now(), null);
        repository.save(entity);
        repository.save(woProjections);
    }

    @Test
    void testFindCurrentAndUpcomingMovies() {
        List<MovieEntity> movies = repository.findCurrentAndUpcomingMovies();

        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(entity.getMovieID(), movies.get(0).getMovieID());
        assertEquals(entity.getName(), movies.get(0).getName());
        assertEquals(entity.getDescription(), movies.get(0).getDescription());
        assertEquals(entity.getDuration(), movies.get(0).getDuration());
    }

    @Test
    void testFindAllWOProjections() {
        List<MovieEntity> movies = repository.findAllWOProjections();

        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(woProjections.getMovieID(), movies.get(0).getMovieID());
        assertEquals(woProjections.getName(), movies.get(0).getName());
        assertEquals(woProjections.getDescription(), movies.get(0).getDescription());
        assertEquals(woProjections.getDuration(), movies.get(0).getDuration());
    }

}
