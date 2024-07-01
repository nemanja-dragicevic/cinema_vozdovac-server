package com.master.BioskopVozdovac.model;

import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.input.HallData;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HallEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Mock
    private HallEntity entity;

    @Mock
    private SeatEntity seatEntity;

    @BeforeEach
    public void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.seatEntity = new SeatEntity();
        this.entity = HallData.HALL_ENTITY.toBuilder().build();
        this.entity.setSeats(new ArrayList<>(List.of(seatEntity)));
    }

    @Test
    public void testValidHall() {
        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testHallNameNotNull() {
        entity.setHallName(null);

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("hallName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testHallNameNotEmpty() {
        entity.setHallName("");

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("hallName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testRowsCountIsZero() {
        entity.setRowsCount(0);

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("rowsCount", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testRowsCountNegative() {
        entity.setRowsCount(-1);

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("rowsCount", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testSeatsPerRowIsZero() {
        entity.setSeatsPerRow(0);

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("seatsPerRow", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testSeatsPerRowNegative() {
        entity.setSeatsPerRow(-1);

        Set<ConstraintViolation<HallEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("seatsPerRow", violations.iterator().next().getPropertyPath().toString());
    }

}
