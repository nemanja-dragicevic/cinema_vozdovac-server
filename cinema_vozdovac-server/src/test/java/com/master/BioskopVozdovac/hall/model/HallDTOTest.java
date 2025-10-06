package com.master.BioskopVozdovac.hall.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static com.master.BioskopVozdovac.input.HallData.SEAT_DTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HallDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validDTO() {
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(HALL_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameIsNull() {
        HallDTO dto = new HallDTO(1L, null, 4, 4, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("hallName")));
    }

    @Test
    void testNameIsBlank() {
        HallDTO dto = new HallDTO(1L, "", 4, 4, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("hallName")));
    }

    @Test
    void testRowsCountIsNegative() {
        HallDTO dto = new HallDTO(1L, "Sala Uno", -4, 4, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rowsCount")));
    }

    @Test
    void testRowsCountIsZero() {
        HallDTO dto = new HallDTO(1L, "Sala Uno", 0, 4, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rowsCount")));
    }

    @Test
    void testSeatsCountIsNegative() {
        HallDTO dto = new HallDTO(1L, "Sala Uno", 4, -4, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("seatsPerRow")));
    }

    @Test
    void testSeatsCountIsZero() {
        HallDTO dto = new HallDTO(1L, "Sala Uno", 4, 0, List.of(SEAT_DTO));
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("seatsPerRow")));
    }

    @Test
    void testSeatsIsEmpty() {
        HallDTO dto = new HallDTO(1L, "Sala Uno", 4, 3, List.of());
        Set<ConstraintViolation<HallDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("seats")));
    }

}
