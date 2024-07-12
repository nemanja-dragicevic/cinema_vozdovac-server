package com.master.BioskopVozdovac.role.service;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.movie.model.MovieDTO;
import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import com.master.BioskopVozdovac.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleAdapter roleAdapter;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private RoleService roleService;

    private RoleEntity roleEntity;
    private MovieEntity movieEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movieEntity = new MovieEntity();
        movieEntity.setMovieID(1L);
        ActorEntity actorEntity = new ActorEntity();

        roleEntity = new RoleEntity();
        roleEntity.setRoleID(1L);
        roleEntity.setMovie(movieEntity);
        roleEntity.setActor(actorEntity);
        roleEntity.setRoleName("Test");
    }

    @Test
    void testGetAllRoles() {
        Set<RoleEntity> entities = new HashSet<>();
        RoleEntity role = new RoleEntity();
        role.setRoleID(1L);
        entities.add(role);

        Set<RoleDTO> dtos = new HashSet<>();
        RoleDTO dto = new RoleDTO();
        dto.setRoleID(1L);
        dtos.add(dto);

        when(roleRepository.findAll()).thenReturn(new ArrayList<>(entities));
        when(roleAdapter.toDTOs(entities)).thenReturn(dtos);

        Set<RoleDTO> result = roleService.getAllRoles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertThat(result.iterator().next().getRoleID()).isEqualTo(1L);
    }

    @Test
    void testSaveRole() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleID(1L);
        roleDTO.setMovie(new MovieDTO());
        roleDTO.setActor(new ActorDTO());

        when(roleAdapter.toEntity(any())).thenReturn(roleEntity);
        when(roleRepository.saveAndFlush(any())).thenReturn(roleEntity);
        when(roleAdapter.toDTO(any())).thenReturn(roleDTO);

        RoleDTO result = roleService.saveRole(roleDTO);

        assertNotNull(result);
    }

    @Test
    @Transactional
    void testUpdateAllRoles() {
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleEntity);

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movieEntity));
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        assertDoesNotThrow(() -> roleService.updateAllRoles(roles, 1L));

        verify(movieRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void testGetRolesForMovie() {
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(roleEntity);

        Set<RoleDTO> roleDTOS = new HashSet<>();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleID(1L);
        roleDTOS.add(roleDTO);

        when(roleRepository.getRolesForMovieWithID(anyLong())).thenReturn(roleEntities);
        when(roleAdapter.toDTOs(roleEntities)).thenReturn(roleDTOS);

        Set<RoleDTO> dtos = roleService.getRolesForMovie(1L);

        assertNotNull(dtos);
        assertThat(dtos).hasSize(1);
        assertThat(dtos.iterator().next().getRoleID()).isEqualTo(1L);
    }

}
