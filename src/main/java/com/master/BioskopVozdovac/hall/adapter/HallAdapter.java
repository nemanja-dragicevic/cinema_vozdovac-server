package com.master.BioskopVozdovac.hall.adapter;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.seat.adapter.SeatAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HallAdapter {

    private final SeatAdapter seatAdapter;

    public HallDTO entityToDTO(final HallEntity entity) {
        if (entity == null)
            return null;

        final HallDTO dto = new HallDTO();
        dto.setHallID(entity.getHallID());
        dto.setRowsCount(entity.getRowsCount());
        dto.setSeatsPerRow(entity.getSeatsPerRow());
        dto.setHallName(entity.getHallName());
        dto.setSeats(seatAdapter.toDTOs(entity.getSeats()));

        return dto;
    }

    public HallEntity dtoToEntity(final HallDTO dto) {
        if (dto == null)
            return null;

        final HallEntity entity = new HallEntity();
        entity.setHallID(dto.getHallID());
        entity.setRowsCount(dto.getRowsCount());
        entity.setSeatsPerRow(dto.getSeatsPerRow());
        entity.setHallName(dto.getHallName());

        return entity;
    }

    public List<HallDTO> toDTO(final List<HallEntity> entities) {
        if (entities == null)
            return null;

        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
