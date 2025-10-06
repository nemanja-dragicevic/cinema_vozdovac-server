package com.master.BioskopVozdovac.role.repository;

import com.master.BioskopVozdovac.role.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "select * from roles where movie_id = ?1", nativeQuery = true)
    Set<RoleEntity> getRolesForMovieWithID(Long movieID);

}
