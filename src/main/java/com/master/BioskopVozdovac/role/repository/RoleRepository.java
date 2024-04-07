package com.master.BioskopVozdovac.role.repository;

import com.master.BioskopVozdovac.role.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "select * from roles where movie_id = ?1", nativeQuery = true)
    List<RoleEntity> getRolesForMovieWithID(Long movieID);

//    @Query(value = "select r.\"id\", r.movie_id, r.actor_id, r.role_name, a.first_name, a.last_name, a.gender " +
//            "from actor a " +
//            "join roles r on r.actor_id = a.id " +
//            "where r.movie_id = ?1", nativeQuery = true)
//    List<RoleEntity> findByMovieID(Long movieID);

//    @Query(value = "SELECT new com.master.BioskopVozdovac.actor.model.ActorEntity(a.id AS id, a.first_name AS first_name, a.last_name AS last_name, a.gender AS gender) " +
//            "FROM actor a " +
//            "JOIN roles r ON r.actor_id = a.id " +
//            "WHERE r.movie_id = ?1", nativeQuery = true)
//    List<Object[]> findByMovieID(Long movieID);

//    @Query(value = "SELECT a.id AS id, a.first_name AS first_name, a.last_name AS last_name, a.gender AS gender, r.* " +
//            "FROM actor a " +
//            "JOIN roles r ON r.actor_id = a.id " +
//            "WHERE r.movie_id = ?1", nativeQuery = true)
//    List<ActorEntity> findByMovieID(Long movieID);

//    @Query(value = "select r from RoleEntity r where r.movie.movieID = ?1")
//    List<RoleEntity> findByMovieID(Long movieID);

}
