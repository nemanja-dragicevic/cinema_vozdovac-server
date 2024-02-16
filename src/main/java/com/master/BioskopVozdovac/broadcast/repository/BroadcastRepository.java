package com.master.BioskopVozdovac.broadcast.repository;

import com.master.BioskopVozdovac.broadcast.model.BroadcastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastRepository extends JpaRepository<BroadcastEntity, Long> {



}
