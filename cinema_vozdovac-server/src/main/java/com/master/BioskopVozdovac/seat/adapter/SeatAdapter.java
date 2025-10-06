package com.master.BioskopVozdovac.seat.adapter;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import com.master.BioskopVozdovac.seat.model.SeatEntity;

import java.util.Collections;
import java.util.List;

public final class SeatAdapter {

    private SeatAdapter() {
        throw new AssertionError();
    }

    public static SeatDTO entityToDTO(final SeatEntity entity) {
        if (entity == null)
            return null;

        return new SeatDTO(entity.getId(), null, entity.getRowNumber(), entity.getSeatNumber());
    }

    public static List<SeatDTO> toDTOs(final List<SeatEntity> entities) {
        if (entities == null)
            return Collections.emptyList();

        return entities.stream().map(SeatAdapter::entityToDTO).toList();
    }

}
