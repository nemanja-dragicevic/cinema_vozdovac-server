package com.master.BioskopVozdovac.movie.model;

import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.genre.model.GenreEntity;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.project.model.ProjectEntity;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieEntityTest {

    private ValidatorFactory validatorFactory;
    private Validator validator;

    private MovieEntity movieEntity;

    @BeforeEach
    void setUp() {
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();

        movieEntity = new MovieEntity(
                1L,
                "Test Movie",
                "Test Description",
                120,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    @Test
    void testValidMovieEntity() {
        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName() {
        movieEntity.setName("");

        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testNameNotNull() {
        movieEntity.setName(null);

        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testInvalidDescription() {
        movieEntity.setDescription("");

        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void testNegativeDuration() {
        movieEntity.setDuration(-10);

        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movieEntity);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("duration")));
    }

    @Test
    void testRelations() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleID(1L);
        roleEntity.setRoleName("Main role");
        roleEntity.setActor(new ActorEntity());

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setGenreID(1L);
        genreEntity.setName("Action");

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setPrice(1000L);
        projectEntity.setProjectID(1L);
        projectEntity.setTime(LocalDateTime.now());
        projectEntity.setHall(new HallEntity());

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);

        Set<GenreEntity> genres = new HashSet<>();
        genres.add(genreEntity);

        Set<ProjectEntity> projects = new HashSet<>();
        projects.add(projectEntity);

        movieEntity.setGenres(genres);
        movieEntity.setProjects(projects);
        movieEntity.setRoles(roles);

        assertEquals(roles, movieEntity.getRoles());
        assertEquals(genres, movieEntity.getGenres());
        assertEquals(projects, movieEntity.getProjects());
    }

}