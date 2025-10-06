package com.master.BioskopVozdovac.hall.adapter;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.seat.adapter.SeatAdapter;

import java.util.List;

public final class HallAdapter {

    private HallAdapter(){
        throw new AssertionError();
    }

    public static HallDTO entityToDTO(final HallEntity entity) {
        if (entity == null)
            return null;

        return HallDTO.builder()
                .hallID(entity.getHallID())
                .hallName(entity.getHallName())
                .rowsCount(entity.getRowsCount())
                .seatsPerRow(entity.getSeatsPerRow())
                .seats(SeatAdapter.toDTOs(entity.getSeats()))
                .build();
    }

    public static HallEntity dtoToEntity(final HallDTO dto) {
        if (dto == null)
            return null;

        final HallEntity entity = new HallEntity();
        entity.setHallID(dto.hallID());
        entity.setRowsCount(dto.rowsCount());
        entity.setSeatsPerRow(dto.seatsPerRow());
        entity.setHallName(dto.hallName());

        return entity;
    }

    public static List<HallDTO> toDTO(final List<HallEntity> entities) {
        if (entities == null)
            return List.of();

        return entities.stream().map(HallAdapter::entityToDTO).toList();
    }

}
