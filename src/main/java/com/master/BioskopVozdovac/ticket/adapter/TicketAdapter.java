package com.master.BioskopVozdovac.ticket.adapter;

import com.master.BioskopVozdovac.member.adapter.MemberAdapter;
import com.master.BioskopVozdovac.ticket.model.TicketDTO;
import com.master.BioskopVozdovac.ticket.model.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketAdapter {

    private final MemberAdapter memberAdapter;

    public TicketDTO entityToDTO(final TicketEntity entity) {
        if (entity == null)
            return null;

        final TicketDTO dto = new TicketDTO();
        dto.setId(entity.getId());
        dto.setMember(memberAdapter.entityToDTO(entity.getMember()));
        dto.setPrice(entity.getPrice());
        dto.setPayinTime(entity.getPayinTime());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public TicketEntity dtoToEntity(final TicketDTO dto) {
        if (dto == null)
            return null;

        final TicketEntity entity = new TicketEntity();
        entity.setId(dto.getId());
        entity.setMember(memberAdapter.dtoToEntity(dto.getMember()));
        entity.setPrice(dto.getPrice());
        entity.setPayinTime(dto.getPayinTime());
        entity.setStatus(dto.getStatus());

        return entity;
    }

}
