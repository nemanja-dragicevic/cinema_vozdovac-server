package com.master.BioskopVozdovac.hall.adapter;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.master.BioskopVozdovac.input.HallData.HALL_DTO;
import static com.master.BioskopVozdovac.input.HallData.HALL_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HallAdapterTest {

    @Test
    void testEntityToDTO() {
        HallDTO dto = HallAdapter.entityToDTO(HALL_ENTITY);

        assertNotNull(dto);
        assertEquals(HALL_ENTITY.getHallID(), dto.hallID());
        assertEquals(HALL_ENTITY.getHallName(), dto.hallName());
        assertEquals(HALL_ENTITY.getSeats().size(), dto.seats().size());
    }

    @Test
    void testDTOToEntity() {
        HallEntity entity = HallAdapter.dtoToEntity(HALL_DTO);

        assertNotNull(entity);
        assertEquals(HALL_DTO.hallID(), entity.getHallID());
        assertEquals(HALL_DTO.hallName(), entity.getHallName());
    }

    @Test
    void testToDTO() {
        List<HallEntity> entities = List.of(HALL_ENTITY);

        List<HallDTO> dtos = HallAdapter.toDTO(entities);
        assertNotNull(dtos);
        assertEquals(entities.size(), dtos.size());
    }

}
