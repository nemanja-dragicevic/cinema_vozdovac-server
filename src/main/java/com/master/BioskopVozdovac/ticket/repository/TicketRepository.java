package com.master.BioskopVozdovac.ticket.repository;

import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

}
