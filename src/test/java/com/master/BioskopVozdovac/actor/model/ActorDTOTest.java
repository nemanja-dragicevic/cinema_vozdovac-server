package com.master.BioskopVozdovac.actor.model;

import com.master.BioskopVozdovac.enums.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActorDTOTest {

    private Validator validator;

    private ActorDTO dto;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validDTO() {
        dto = new ActorDTO(1L, "Pera", "Peric", Gender.MALE);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testFirstNameIsNull() {
        dto = new ActorDTO(1L, null, "Peric", Gender.MALE);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testFirstNameIsBlank() {
        dto = new ActorDTO(1L, "   ", "Peric", Gender.MALE);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testlastNameIsNull() {
        dto = new ActorDTO(1L, "Pera", null, Gender.MALE);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void testLastNameIsBlank() {
        dto = new ActorDTO(1L, "Pera", "   ", Gender.MALE);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void testGenderIsNull() {
        dto = new ActorDTO(1L, "Pera", null, null);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("gender")));
    }

}
