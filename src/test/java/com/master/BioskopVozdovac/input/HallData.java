package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;

public class HallData {

    public static final HallEntity HALL_ENTITY = HallEntity.builder()
            .hallID(0L)
            .hallName("Sala Uno")
            .rowsCount(5)
            .seatsPerRow(5)
            .build();

    public static final HallDTO HALL_DTO = HallDTO.builder()
            .hallID(0L)
            .hallName("Sala Uno")
            .rowsCount(5)
            .seatsPerRow(5)
            .build();

}
