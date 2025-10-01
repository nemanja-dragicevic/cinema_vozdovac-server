package com.master.BioskopVozdovac.project.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static com.master.BioskopVozdovac.input.MovieData.MOVIE_DTO;
import static com.master.BioskopVozdovac.input.MovieData.PROJECT_DTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidDTO() {
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(PROJECT_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testMovieIsNull() {
        ProjectDTO dto = ProjectDTO.builder()
                .id(1L)
                .movie(null)
                .hall(HALL_DTO)
                .projectTime(LocalDateTime.of(2025, 10, 11, 15, 0))
                .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
                .price(1000L)
                .build();
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("movie")));
    }

    @Test
    void testHallIsNull() {
        ProjectDTO dto = ProjectDTO.builder()
                .id(1L)
                .movie(MOVIE_DTO)
                .hall(null)
                .projectTime(LocalDateTime.of(2025, 10, 11, 15, 0))
                .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
                .price(1000L)
                .build();
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("hall")));
    }

    @Test
    void testProjectTimeIsNull() {
        ProjectDTO dto = ProjectDTO.builder()
                .id(1L)
                .movie(MOVIE_DTO)
                .hall(HALL_DTO)
                .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
                .price(1000L)
                .build();
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("projectTime")));
    }

    @Test
    void testProjectEndIsNull() {
        ProjectDTO dto = ProjectDTO.builder()
                .id(1L)
                .movie(MOVIE_DTO)
                .hall(HALL_DTO)
                .projectTime(LocalDateTime.of(2025, 10, 11, 15, 0))
                .price(1000L)
                .build();
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("projectEnd")));
    }

    @Test
    void testPriceIsZero() {
        ProjectDTO dto = ProjectDTO.builder()
                .id(1L)
                .movie(MOVIE_DTO)
                .hall(HALL_DTO)
                .projectTime(LocalDateTime.of(2025, 10, 11, 15, 0))
                .projectEnd(LocalDateTime.of(2025, 10, 11, 17, 0))
                .price(0L)
                .build();
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("price")));
    }

}
