package com.master.BioskopVozdovac.genre.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenreEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private GenreEntity genreEntity;

    @BeforeEach
    void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.genreEntity = new GenreEntity();
        genreEntity.setGenreID(1L);
        genreEntity.setName("Action");
    }

    @Test
    void testValidGenreEntity() {
        Set<ConstraintViolation<GenreEntity>> violations = validator.validate(genreEntity);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testGenreEntityEmptyName() {
        genreEntity.setName("");

        Set<ConstraintViolation<GenreEntity>> violations = validator.validate(genreEntity);
        assertEquals(1, violations.size());

        ConstraintViolation<GenreEntity> violation = violations.iterator().next();
        assertEquals("must not be empty", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void testGenreEntityNameIsNull() {
        genreEntity.setName(null);

        Set<ConstraintViolation<GenreEntity>> violations = validator.validate(genreEntity);
        assertEquals(1, violations.size());

        ConstraintViolation<GenreEntity> violation = violations.iterator().next();
        assertEquals("must not be empty", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

}
