package com.master.BioskopVozdovac.seat.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.master.BioskopVozdovac.input.HallData.SEAT_DTO;
import static org.junit.jupiter.api.Assertions.*;

class SeatDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<SeatDTO>> violations = validator.validate(SEAT_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testHallIDIsNull() {
        SeatDTO dto = new SeatDTO(1L, null, 1, 1);
        Set<ConstraintViolation<SeatDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("hallID")));
    }

    @Test
    void testHallRowIsNegative() {
        SeatDTO dto = new SeatDTO(1L, 1L, -1, 1);
        Set<ConstraintViolation<SeatDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("rowNumber")));
    }

    @Test
    void testHallSeatIsNegative() {
        SeatDTO dto = new SeatDTO(1L, 1L, 1, -1);
        Set<ConstraintViolation<SeatDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("seatNumber")));
    }

}
