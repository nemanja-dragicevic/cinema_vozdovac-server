package com.master.BioskopVozdovac.role.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.master.BioskopVozdovac.input.MovieData.ROLE_DTO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValid() {
        Set<ConstraintViolation<RoleDTO>> violations = validator.validate(ROLE_DTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testActorUnknown() {
        RoleDTO dto = new RoleDTO(1L, null, 2L, "Frank");
        Set<ConstraintViolation<RoleDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("actorID")));
    }

    @Test
    void testRoleInvalid() {
        RoleDTO dto = new RoleDTO(1L, 1L, 2L, "  ");
        Set<ConstraintViolation<RoleDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("roleName")));
    }

}
