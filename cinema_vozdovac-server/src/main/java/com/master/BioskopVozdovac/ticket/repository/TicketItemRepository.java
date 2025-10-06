package com.master.BioskopVozdovac.ticket.repository;

import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketItemRepository extends JpaRepository<TicketItemEntity, Long> {
}
