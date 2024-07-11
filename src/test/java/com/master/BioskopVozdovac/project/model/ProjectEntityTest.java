package com.master.BioskopVozdovac.project.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    void testValidEntity() {
        MovieEntity movie = new MovieEntity();
        movie.setMovieID(1L);
        HallEntity hall = new HallEntity();
        hall.setHallID(1L);

        ProjectEntity project = new ProjectEntity();
        project.setMovie(movie);
        project.setHall(hall);
        project.setTime(LocalDateTime.now());
        project.setProjectEnd(LocalDateTime.now().plusHours(2));
        project.setPrice(100L);

        Set<ConstraintViolation<ProjectEntity>> violations = validator.validate(project);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEntity() {
        ProjectEntity project = new ProjectEntity();

        Set<ConstraintViolation<ProjectEntity>> violations = validator.validate(project);

//        violations.forEach(violation -> {
//            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
//        });
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }

    @Test
    void testNegativePrice() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectID(1L);
        projectEntity.setTime(LocalDateTime.now());
        projectEntity.setProjectEnd(LocalDateTime.now());
        projectEntity.setMovie(new MovieEntity());
        projectEntity.setPrice(-1L);

        Set<ConstraintViolation<ProjectEntity>> violations = validator.validate(projectEntity);

        assertFalse(violations.isEmpty());
        assertEquals("price", violations.iterator().next().getPropertyPath().toString());
    }

}
