package com.master.BioskopVozdovac.role.service;

import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import com.master.BioskopVozdovac.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleAdapter roleAdapter;

    public List<RoleDTO> getAllRoles() {
        return roleAdapter.toDTOs(roleRepository.findAll());
    }

    public RoleDTO saveRole(RoleDTO dto) {
        RoleEntity entity = roleRepository.saveAndFlush(roleAdapter.toEntity(dto));
        return roleAdapter.toDTO(entity);
    }
}
