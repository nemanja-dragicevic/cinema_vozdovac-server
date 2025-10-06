package com.master.BioskopVozdovac.project.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static com.master.BioskopVozdovac.input.MovieData.MOVIE_DTO;
import static com.master.BioskopVozdovac.input.MovieData.PROJECTIONS_SAVE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectionsSaveTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<ProjectionsSave>> violations = validator.validate(PROJECTIONS_SAVE);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testProjectionTimeEmpty() {
        ProjectionsSave object = new ProjectionsSave(1L, MOVIE_DTO, HALL_DTO,
                List.of(), 1000L);
        Set<ConstraintViolation<ProjectionsSave>> violations = validator.validate(object);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("projectionTime")));
    }

}
