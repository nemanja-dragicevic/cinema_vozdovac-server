package com.master.BioskopVozdovac.ticket.adapter;

import com.master.BioskopVozdovac.project.repository.ProjectRepository;
import com.master.BioskopVozdovac.seat.repository.SeatRepository;
import com.master.BioskopVozdovac.ticket.repository.TicketRepository;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketItemAdapter {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ProjectRepository projectRepository;

    public TicketItemDTO toDTO(final TicketItemEntity entity) {
        if (entity == null)
            return null;

        final TicketItemDTO dto = new TicketItemDTO();
        dto.setId(entity.getId());
        dto.setProjectId(entity.getProject().getProjectID());
        dto.setSeatId(entity.getSeat().getId());

        return dto;
    }

    public TicketItemEntity entityToDTO(final TicketItemDTO dto) {
        if (dto == null)
            return null;

        final TicketItemEntity entity = new TicketItemEntity();
        entity.setId(dto.getId());
        entity.setSeat(seatRepository.findById(dto.getSeatId()).orElseThrow(
                () -> new RuntimeException("Seat not found")
        ));
        entity.setProject(projectRepository.findById(dto.getProjectId()).orElseThrow(
                () -> new RuntimeException("Projection not found")
        ));

        return entity;
    }

}
