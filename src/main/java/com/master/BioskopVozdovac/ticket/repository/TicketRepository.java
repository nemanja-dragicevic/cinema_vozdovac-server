package com.master.BioskopVozdovac.ticket.repository;

import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByMemberMemberID(final Long id);
}
