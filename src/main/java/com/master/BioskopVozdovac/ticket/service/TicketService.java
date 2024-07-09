package com.master.BioskopVozdovac.ticket.service;

import com.master.BioskopVozdovac.enums.TicketStatus;
import com.master.BioskopVozdovac.project.adapter.ProjectAdapter;
import com.master.BioskopVozdovac.seat.adapter.SeatAdapter;
import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItems;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketAdapter ticketAdapter;
    private final TicketRepository ticketRepository;
    private final ProjectAdapter projectAdapter;
    private final SeatAdapter seatAdapter;

    public List<TicketDTO> getTicketsUser(Long id) {
        List<TicketEntity> entities = ticketRepository.findByMemberMemberID(id);
        return ticketAdapter.toDTOs(entities);
    }

    public List<TicketItems> getTicketItems(Long id) {
        TicketEntity ticket = ticketRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There is no such ticket")
        );
        List<TicketItems> ticketItems = new ArrayList<>();
        for (TicketItemEntity ticketItem: ticket.getTicketItems()) {
            TicketItems item = new TicketItems();
            item.setProjectDTO(projectAdapter.entityToDTO(ticketItem.getProject()));
            item.setSeatDTO(seatAdapter.entityToDTO(ticketItem.getSeat()));
            ticketItems.add(item);
        }
        return ticketItems;
    }

    public TicketDTO sendForApproval(Long memberID, Long id) {
        TicketEntity ticket = ticketRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There is no such ticket")
        );
        if (!ticket.getMember().getMemberID().equals(memberID))
            throw new IllegalArgumentException("Please select your ticket");

        ticket.setStatus(TicketStatus.UNDER_REVIEW);
        ticketRepository.saveAndFlush(ticket);

        return ticketAdapter.entityToDTO(ticket);
    }

    public List<TicketDTO> getRefundRequests() {
        List<TicketEntity> entities = ticketRepository.findByStatus(TicketStatus.UNDER_REVIEW);
        return entities.stream().map(ticketAdapter::entityToDTO).collect(Collectors.toList());
    }

    public List<TicketItems> getBookedSeatsForProjection(Long id) {
        List<TicketItemEntity> items = ticketRepository.findByStatusAndProjectID(TicketStatus.PAID, id);
        List<TicketItems> itemsList = new ArrayList<>();
        for (TicketItemEntity item : items) {
            TicketItems ti = new TicketItems();
            ti.setProjectDTO(projectAdapter.entityToDTO(item.getProject()));
            ti.setSeatDTO(seatAdapter.entityToDTO(item.getSeat()));
            itemsList.add(ti);
        }
        return itemsList;
    }
    
}
