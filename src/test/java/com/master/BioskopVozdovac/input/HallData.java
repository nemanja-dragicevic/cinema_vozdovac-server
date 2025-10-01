package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import com.master.BioskopVozdovac.seat.model.SeatDTO;
import com.master.BioskopVozdovac.seat.model.SeatEntity;

import java.util.List;

public class HallData {

    public static final SeatEntity SEAT_ENTITY = SeatEntity.builder()
            .id(1L)
            .rowNumber(1)
            .seatNumber(1)
            .build();

    public static final SeatDTO SEAT_DTO = new SeatDTO(1L, 1L, 2, 3);

    public static final HallEntity HALL_ENTITY = HallEntity.builder()
            .hallID(1L)
            .hallName("Sala Uno")
            .rowsCount(5)
            .seatsPerRow(5)
            .seats(List.of(SEAT_ENTITY))
            .build();

    public static final HallDTO HALL_DTO = HallDTO.builder()
            .hallID(1L)
            .hallName("Sala Uno")
            .rowsCount(5)
            .seatsPerRow(5)
            .seats(List.of(SEAT_DTO))
            .build();

}
