package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.input.MemberData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private MemberEntity entity;

    @BeforeEach
    public void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.entity = MemberData.MEMBER_ENTITY.toBuilder().build();
    }

    @Test
    public void testValidMember() {
        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testFirstNameNotNull() {
        entity.setFirstName(null);

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testFirstNameNotEmpty() {
        entity.setFirstName("");

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testLastNameNotNull() {
        entity.setLastName(null);

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testLastNameNotEmpty() {
        entity.setLastName("");

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testUsernameNotNull() {
        entity.setUsername(null);

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("username", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testUsernameNotEmpty() {
        entity.setUsername("");

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("username", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testInvalidUsernamePattern() {
        entity.setUsername("testUsername!/-+");

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("username", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testPasswordNotNull() {
        entity.setPassword(null);

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("password", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testEmptyPassword() {
        entity.setPassword("");

        Set<ConstraintViolation<MemberEntity>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
        assertEquals("password", violations.iterator().next().getPropertyPath().toString());
    }

}
