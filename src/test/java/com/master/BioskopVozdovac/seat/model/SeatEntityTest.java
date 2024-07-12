package com.master.BioskopVozdovac.seat.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeatEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    private SeatEntity seatEntity;

    @BeforeEach
    void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        HallEntity hall = new HallEntity();
        hall.setHallID(1L);
        hall.setHallName("Main Hall");

        seatEntity = new SeatEntity();
        seatEntity.setId(1L);
        seatEntity.setHall(hall);
        seatEntity.setSeatNumber(5);
        seatEntity.setRowNumber(5);
    }

    @Test
    void testValidEntity() {
        Set<ConstraintViolation<SeatEntity>> violations = validator.validate(seatEntity);

        assertEquals(violations.size(), 0);
    }

    @Test
    void testRowIsNegative() {
        seatEntity.setRowNumber(-5);

        Set<ConstraintViolation<SeatEntity>> violations = validator.validate(seatEntity);

        assertEquals(violations.size(), 1);
    }

    @Test
    void testSeatIsNegative() {
        seatEntity.setSeatNumber(-5);

        Set<ConstraintViolation<SeatEntity>> violations = validator.validate(seatEntity);

        assertEquals(violations.size(), 1);
    }

    @Test
    void testHallIsNull() {
        seatEntity.setHall(null);

        Set<ConstraintViolation<SeatEntity>> violations = validator.validate(seatEntity);

        assertEquals(violations.size(), 1);
    }

}
