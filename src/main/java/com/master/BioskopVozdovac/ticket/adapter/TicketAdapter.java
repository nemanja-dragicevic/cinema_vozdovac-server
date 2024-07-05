package com.master.BioskopVozdovac.ticket.adapter;

import com.master.BioskopVozdovac.member.repository.MemberRepository;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import com.master.BioskopVozdovac.ticket.model.TicketItemDTO;
import com.master.BioskopVozdovac.ticket.model.TicketItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketAdapter {

    private final MemberRepository memberRepository;
    private final TicketItemAdapter ticketItemAdapter;

    public TicketDTO entityToDTO(final TicketEntity entity) {
        if (entity == null)
            return null;

        final TicketDTO dto = new TicketDTO();
        dto.setId(entity.getId());
        dto.setMemberID(entity.getMember().getMemberID());
        dto.setTotal(entity.getTotal());
        dto.setPayinTime(entity.getPayinTime());
        dto.setStatus(entity.getStatus());
        dto.setTotalSeats(entity.getTotalSeats());

        return dto;
    }

    public TicketEntity dtoToEntity(final TicketDTO dto) {
        if (dto == null)
            return null;

        final TicketEntity entity = new TicketEntity();
        entity.setId(dto.getId());
        entity.setMember(memberRepository.findById(dto.getMemberID()).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
        entity.setTotal(dto.getTotal());
        entity.setPayinTime(dto.getPayinTime());
        entity.setStatus(dto.getStatus());
        entity.setTotalSeats(dto.getTotalSeats());
        entity.setTicketItems(prepareTicketItems(entity, dto.getTicketItems()));

        return entity;
    }

    private Set<TicketItemEntity> prepareTicketItems(final TicketEntity ticket, final Set<TicketItemDTO> ticketItems) {
        return ticketItems.stream().map(ticketItemDTO -> {
            TicketItemEntity item = ticketItemAdapter.dtoToEntity(ticketItemDTO);
            item.setTicket(ticket);
            return item;
        }).collect(Collectors.toSet());
    }

}
