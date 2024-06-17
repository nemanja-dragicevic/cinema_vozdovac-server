package com.master.BioskopVozdovac.ticket.adapter;

import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketItemAdapter {

    private final TicketRepository ticketRepository;

    public TicketItemDTO toDTO(final TicketItemEntity entity) {
        if (entity == null)
            return null;

        final TicketItemDTO dto = new TicketItemDTO();
        dto.setId(entity.getId());
        dto.setTicketId(entity.getTicket().getId());
        dto.setNumberOfSeats(entity.getNumberOfSeats());

        return dto;
    }

    public TicketItemEntity entityToDTO(final TicketItemDTO dto) {
        if (dto == null)
            return null;

        final TicketItemEntity entity = new TicketItemEntity();
        entity.setId(dto.getId());
        entity.setTicket(ticketRepository.findById(dto.getTicketId()).orElse(null));
        entity.setNumberOfSeats(dto.getNumberOfSeats());

        return entity;
    }

}
