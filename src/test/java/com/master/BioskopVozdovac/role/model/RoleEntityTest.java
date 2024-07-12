package com.master.BioskopVozdovac.role.model;

import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    private ActorEntity actorEntity;
    private MovieEntity movieEntity;

    @BeforeEach
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        actorEntity = new ActorEntity();
        actorEntity.setActorID(1L);
        actorEntity.setFirstName("Jackie");
        actorEntity.setLastName("Chan");
        actorEntity.setGender(Gender.MALE);

        movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        movieEntity.setName("Police story");
        movieEntity.setDuration(100);
    }

    @Test
    void testValidEntity() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleID(1L);
        roleEntity.setRoleName("Police inspector");
        roleEntity.setActor(actorEntity);
        roleEntity.setMovie(movieEntity);

        Set<ConstraintViolation<RoleEntity>> violations = validator.validate(roleEntity);
        assertThat(violations).isEmpty();
    }

    @Test
    void testActorIsNull() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setMovie(movieEntity);
        roleEntity.setRoleID(1L);
        roleEntity.setRoleName("Main role");

        Set<ConstraintViolation<RoleEntity>> violations = validator.validate(roleEntity);
        assertThat(violations).hasSize(1);
    }

    @Test
    void testMovieIsNull() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setActor(actorEntity);
        roleEntity.setRoleID(1L);
        roleEntity.setRoleName("Main role");

        Set<ConstraintViolation<RoleEntity>> violations = validator.validate(roleEntity);
        assertThat(violations).hasSize(1);
    }

    @Test
    void testRoleNameIsNull() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setMovie(movieEntity);
        roleEntity.setActor(actorEntity);
        roleEntity.setRoleID(1L);

        Set<ConstraintViolation<RoleEntity>> violations = validator.validate(roleEntity);
        assertThat(violations).hasSize(1);
    }

    @Test
    void testAllIsNull() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleID(1L);

        Set<ConstraintViolation<RoleEntity>> violations = validator.validate(roleEntity);
        assertThat(violations).hasSize(3);
    }

}
