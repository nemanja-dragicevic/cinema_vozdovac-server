package com.master.BioskopVozdovac.movie.repository;

import com.master.BioskopVozdovac.movie.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("select m from MovieEntity m where m.startTime <= current_date")
    List<MovieEntity> findAllShowing();

    @Query("select m from MovieEntity m where m.startTime > current_date")
    List<MovieEntity> findFutureMovies();
}
