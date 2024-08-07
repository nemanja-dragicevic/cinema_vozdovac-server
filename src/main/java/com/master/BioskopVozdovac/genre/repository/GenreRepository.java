package com.master.BioskopVozdovac.genre.repository;

import com.master.BioskopVozdovac.genre.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

}
