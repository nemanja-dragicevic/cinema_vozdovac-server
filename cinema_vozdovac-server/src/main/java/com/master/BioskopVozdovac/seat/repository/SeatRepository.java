package com.master.BioskopVozdovac.seat.repository;

import com.master.BioskopVozdovac.seat.model.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

}
