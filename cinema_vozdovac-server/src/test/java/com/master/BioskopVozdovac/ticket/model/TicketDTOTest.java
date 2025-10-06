package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static com.master.BioskopVozdovac.input.TicketData.TICKET_DTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(TICKET_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testMemberUnknown() {
        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(TicketDTO.builder()
                .id(1L)
                .payinTime(LocalDateTime.of(2025, 10, 1, 16, 12))
                .total(500L)
                .status(TicketStatus.PAID)
                .totalSeats(2)
                .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("memberID")));
    }

    @Test
    void testTotalIsNegative() {
        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(TicketDTO.builder()
                .id(1L)
                .memberID(1L)
                .payinTime(LocalDateTime.of(2025, 10, 1, 16, 12))
                .total(-500L)
                .status(TicketStatus.PAID)
                .totalSeats(2)
                .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("total")));
    }

    @Test
    void testTotaSeatsIsZero() {
        Set<ConstraintViolation<TicketDTO>> violations = validator.validate(TicketDTO.builder()
                .id(1L)
                .memberID(1L)
                .payinTime(LocalDateTime.of(2025, 10, 1, 16, 12))
                .total(1000L)
                .status(TicketStatus.PAID)
                .totalSeats(0)
                .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("totalSeats")));
    }

}
