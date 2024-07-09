package com.master.BioskopVozdovac.ticket.model;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.member.model.MemberEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private TicketEntity entity;

    @Mock
    private MemberEntity memberEntity;
    @Mock
    private TicketItemEntity ticketItemEntity;

    @Before
    public void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.memberEntity = new MemberEntity();

        this.entity = new TicketEntity();
        this.entity.setId(1L);
        this.entity.setStatus(TicketStatus.NOT_PAID);
        this.entity.setPayinTime(LocalDateTime.now());
        this.entity.setTotal(1000L);
        this.entity.setTicketItems(new HashSet<>());
        this.entity.getTicketItems().add(ticketItemEntity);
        this.entity.setTotalSeats(1);
        this.entity.setMember(memberEntity);
        this.entity.setPaymentIntent("abc123");
    }

    @Test
    public void testValidTicket() {
        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMemberNotNull() {
        entity.setMember(null);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("member", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testPayinTimeNotNull() {
        entity.setPayinTime(null);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("payinTime", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testTotalIsPositive() {
        entity.setTotal(0L);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("total", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testStatusNotNull() {
        entity.setStatus(null);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("status", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testTotalSeatsIsPositive() {
        entity.setTotalSeats(0);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("totalSeats", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testTicketItemsNotNull() {
        entity.setTicketItems(null);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("ticketItems", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testTicketItemsNotEmpty() {
        entity.setTicketItems(new HashSet<>());

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("ticketItems", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testPaymentNotNull() {
        entity.setPaymentIntent(null);

        Set<ConstraintViolation<TicketEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("paymentIntent", violations.iterator().next().getPropertyPath().toString());

    }

}
