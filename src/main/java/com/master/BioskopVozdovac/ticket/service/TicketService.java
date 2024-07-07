package com.master.BioskopVozdovac.ticket.service;

import com.master.BioskopVozdovac.ticket.adapter.TicketAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItems;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketAdapter ticketAdapter;
    private final TicketRepository ticketRepository;

    public List<TicketDTO> getTicketsUser(Long id) {
        List<TicketEntity> entities = ticketRepository.findByMemberMemberID(id);
        return ticketAdapter.toDTOs(entities);
    }

    public List<TicketItems> getTicketItems(Long id) {

        return null;
    }
}
