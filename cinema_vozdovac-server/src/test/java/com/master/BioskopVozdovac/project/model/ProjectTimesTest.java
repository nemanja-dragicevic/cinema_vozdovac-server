package com.master.BioskopVozdovac.project.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static com.master.BioskopVozdovac.input.MovieData.PROJECT_TIMES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectTimesTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<ProjectTimes>> violations = validator.validate(PROJECT_TIMES);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testStartIsNull() {
        Set<ConstraintViolation<ProjectTimes>> violations = validator.validate(new ProjectTimes(null, LocalTime.of(17, 0)));
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("start")));
    }

    @Test
    void testEndIsNull() {
        Set<ConstraintViolation<ProjectTimes>> violations = validator.validate(new ProjectTimes(LocalTime.of(15, 0), null));
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("end")));
    }

}
