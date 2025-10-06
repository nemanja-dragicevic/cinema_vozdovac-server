package com.master.BioskopVozdovac.member.model;

import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.enums.MemberRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.master.BioskopVozdovac.input.MemberData.MEMBER_DTO;
import static org.junit.jupiter.api.Assertions.*;

class MemberDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValid() {
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(MEMBER_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidFirstName() {
        MemberDTO dto = MemberDTO.create(1L, null, "Peric", LocalDate.of(2000, 3, 3),
                MemberRole.USER, Gender.MALE, "pera@gmail.com", "perica");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testInvalidLastName() {
        MemberDTO dto = MemberDTO.create(1L, "Pera", " ", LocalDate.of(2000, 3, 3),
                MemberRole.USER, Gender.MALE, "pera@gmail.com", "perica");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void testInvalidEmail() {
        MemberDTO dto = MemberDTO.create(1L, "Pera", "Peric", LocalDate.of(2000, 3, 3),
                MemberRole.USER, Gender.MALE, "   ", "perica");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testInvalidBirthdateFuture() {
        MemberDTO dto = MemberDTO.create(1L, "Pera", "Peric", LocalDate.of(2026, 3, 3),
                MemberRole.USER, Gender.MALE, "pera@gmail.com", "perica");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDate")));
    }

    @Test
    void testInvalidGender() {
        MemberDTO dto = MemberDTO.create(1L, "Pera", "Peric", LocalDate.of(2026, 3, 3),
                MemberRole.USER, null, "pera@gmail.com", "perica");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("gender")));
    }

    @Test
    void testInvalidUsername() {
        MemberDTO dto = MemberDTO.create(1L, "Pera", "Peric", LocalDate.of(2026, 3, 3),
                MemberRole.USER, Gender.MALE, "pera@gmail.com", "");
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDate")));
    }

}
