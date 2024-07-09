package com.master.BioskopVozdovac.ticket.repository;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByMemberMemberID(final Long id);

    List<TicketEntity> findByStatus(final TicketStatus status);

    @Query(value = "SELECT ti FROM TicketEntity t " +
            "join t.ticketItems ti " +
            "where t.status = ?1 " +
            "and ti.project.projectID = ?2")
    List<TicketItemEntity> findByStatusAndProjectID(final TicketStatus status, final Long id);
}
