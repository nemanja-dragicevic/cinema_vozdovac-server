package com.master.BioskopVozdovac.seat.adapter;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import com.master.BioskopVozdovac.seat.model.SeatEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeatAdapter {

    public SeatDTO entityToDTO (final SeatEntity entity) {
        if (entity == null)
            return null;

        final SeatDTO dto = new SeatDTO();
        dto.setId(entity.getId());
        dto.setHallID(entity.getHall().getHallID());
        dto.setRowNumber(entity.getRowNumber());
        dto.setSeatNumber(entity.getSeatNumber());

        return dto;
    }

    public List<SeatDTO> toDTOs(final List<SeatEntity> dtos) {
        if (dtos == null)
            return null;

        return dtos.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
