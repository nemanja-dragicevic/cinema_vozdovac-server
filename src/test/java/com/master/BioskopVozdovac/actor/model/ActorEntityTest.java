package com.master.BioskopVozdovac.actor.model;

import com.master.BioskopVozdovac.input.ActorData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private ActorEntity actorEntity;

    @Before
    public void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        this.actorEntity = ActorData.ACTOR_ENTITY.toBuilder().build();
    }

    @Test
    public void testValidActor() {
        Set<ConstraintViolation<ActorEntity>> violations = validator.validate(actorEntity);

        assertEquals(0, violations.size());
    }

    @Test
    public void testFirstNameNotNull() {
        actorEntity.setFirstName(null);

        Set<ConstraintViolation<ActorEntity>> violations = validator.validate(actorEntity);

        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testFirstNameNotEmpty() {
        actorEntity.setFirstName("");

        Set<ConstraintViolation<ActorEntity>> violations = validator.validate(actorEntity);

        assertEquals(1, violations.size());
        assertEquals("firstName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testLastNameNotNull() {
        actorEntity.setLastName(null);

        Set<ConstraintViolation<ActorEntity>> violations = validator.validate(actorEntity);

        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testLastNameNotEmpty() {
        actorEntity.setLastName(null);

        Set<ConstraintViolation<ActorEntity>> violations = validator.validate(actorEntity);

        assertEquals(1, violations.size());
        assertEquals("lastName", violations.iterator().next().getPropertyPath().toString());
    }

}
