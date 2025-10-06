package com.master.BioskopVozdovac.seat.adapter;

import com.master.BioskopVozdovac.seat.model.SeatDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.master.BioskopVozdovac.input.HallData.SEAT_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SeatAdapterTest {

    @Test
    void testEntityToDTO() {
        SeatDTO dto = SeatAdapter.entityToDTO(SEAT_ENTITY);

        assertNotNull(dto);
        assertEquals(SEAT_ENTITY.getSeatNumber(), dto.seatNumber());
        assertEquals(SEAT_ENTITY.getRowNumber(), dto.rowNumber());
    }

    @Test
    void testToDTOs() {
        List<SeatDTO> dtos = SeatAdapter.toDTOs(List.of(SEAT_ENTITY));

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(SEAT_ENTITY.getSeatNumber(), dtos.get(0).seatNumber());
        assertEquals(SEAT_ENTITY.getRowNumber(), dtos.get(0).rowNumber());
    }

}
