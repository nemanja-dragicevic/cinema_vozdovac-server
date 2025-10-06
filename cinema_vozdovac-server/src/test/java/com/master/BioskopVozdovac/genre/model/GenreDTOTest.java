package com.master.BioskopVozdovac.genre.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenreDTOTest {

    private Validator validator;

    private GenreDTO dto;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidDTO() {
        dto = new GenreDTO(1L, "Action");
        Set<ConstraintViolation<GenreDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameIsNull() {
        dto = new GenreDTO(1L, null);
        Set<ConstraintViolation<GenreDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testNameIsBlank() {
        dto = new GenreDTO(1L, "  ");
        Set<ConstraintViolation<GenreDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

}
