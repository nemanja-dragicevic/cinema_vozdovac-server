package com.master.BioskopVozdovac.actor.repository;

import com.master.BioskopVozdovac.actor.model.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {

}
