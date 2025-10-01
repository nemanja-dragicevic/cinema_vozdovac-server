package com.master.BioskopVozdovac.role.service;

import com.master.BioskopVozdovac.movie.model.MovieEntity;
import com.master.BioskopVozdovac.movie.repository.MovieRepository;
import com.master.BioskopVozdovac.role.adapter.RoleAdapter;
import com.master.BioskopVozdovac.role.model.RoleDTO;
import com.master.BioskopVozdovac.role.model.RoleEntity;
import com.master.BioskopVozdovac.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleAdapter roleAdapter;

    private final MovieRepository movieRepository;

    public Set<RoleDTO> getAllRoles() {
        return roleAdapter.toDTOs(new HashSet<>(roleRepository.findAll()));
    }

    @Transactional
    public RoleDTO saveRole(RoleDTO dto) {
        RoleEntity entity = roleRepository.save(roleAdapter.toEntity(dto));
        return roleAdapter.toDTO(entity);
    }

    @Transactional
    public void updateAllRoles(Set<RoleEntity> roles, Long movieId) {
        if (roles == null || roles.isEmpty()) return;

        // Fetch the MovieEntity based on its ID
        Optional<MovieEntity> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            MovieEntity movieEntity = optionalMovie.get();
            // Update the movie association for each RoleEntity
            for (RoleEntity role : roles) {
                // Set the movie association
                role.setMovie(movieEntity);
                // Save the updated RoleEntity
                roleRepository.save(role);
            }
        }
    }

    public Set<RoleDTO> getRolesForMovie(Long movieID) {
        Set<RoleEntity> entities = roleRepository.getRolesForMovieWithID(movieID);
        return roleAdapter.toDTOs(entities);
    }

}
