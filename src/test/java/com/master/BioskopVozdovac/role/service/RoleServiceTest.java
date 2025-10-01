package com.master.BioskopVozdovac.role.service;

import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import com.master.BioskopVozdovac.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.master.BioskopVozdovac.input.RoleData.ROLE_DTO;
import static com.master.BioskopVozdovac.input.RoleData.ROLE_ENTITY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleAdapter roleAdapter;

    @Test
    void testGetRolesForMovieWithID() {
        when(roleRepository.getRolesForMovieWithID(anyLong()))
                .thenReturn(Set.of(ROLE_ENTITY));

        when(roleAdapter.toDTOs(ArgumentMatchers.any()))
                .thenReturn(Set.of(ROLE_DTO));

        Set<RoleDTO> roleDTOs = roleService.getRolesForMovie(1L);

        assertNotNull(roleDTOs);
        assertEquals(ROLE_ENTITY.getRoleName(),
                roleDTOs.iterator().next().roleName());
    }

    @Test
    void testSaveRole() {
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(ROLE_ENTITY);
        when(roleAdapter.toEntity(any(RoleDTO.class))).thenReturn(ROLE_ENTITY);
        when(roleAdapter.toDTO(any(RoleEntity.class))).thenReturn(ROLE_DTO);

        RoleDTO dto = roleService.saveRole(ROLE_DTO);
        assertNotNull(dto);
        assertEquals(ROLE_ENTITY.getRoleName(), dto.roleName());
        assertEquals(ROLE_ENTITY.getMovie().getMovieID(), dto.movieID());
    }

}
