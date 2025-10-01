package com.master.BioskopVozdovac.movie.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.master.BioskopVozdovac.input.GenreData.GENRE_DTO;
import static com.master.BioskopVozdovac.input.MovieData.MOVIE_DTO;
import static org.junit.jupiter.api.Assertions.*;

class MovieDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(MOVIE_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName() {
        MovieDTO dto = MovieDTO.builder()
                .movieID(0L)
                .startTime(LocalDate.of(2023, 3, 3))
                .build();

        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testInvalidStartTime() {
        MovieDTO dto = MovieDTO.builder()
                .movieID(1L)
                .name("John Wick")
                .endTime(LocalDate.of(2025, 9, 13))
                .description("Keanu Reeves looks for revenge")
                .duration(120)
                .genres(Set.of(GENRE_DTO))
                .build();
        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("startTime")));
    }

    @Test
    void testInvalidEndTime() {
        MovieDTO dto = MOVIE_DTO;
        dto.setEndTime(null);
        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("endTime")));
    }

    @Test
    void testInvalidDescription() {
        MovieDTO dto = MOVIE_DTO;
        dto.setDescription("  ");
        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void testInvalidDuration() {
        MovieDTO dto = MOVIE_DTO;
        dto.setDuration(-120);
        Set<ConstraintViolation<MovieDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("duration")));
    }

}
