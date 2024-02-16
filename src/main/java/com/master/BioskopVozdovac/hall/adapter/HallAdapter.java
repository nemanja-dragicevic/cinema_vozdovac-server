package com.master.BioskopVozdovac.hall.adapter;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import org.springframework.stereotype.Component;

@Component
public class HallAdapter {

    public HallDTO entityToDTO(final HallEntity entity) {
        if (entity == null)
            return null;

        final HallDTO dto = new HallDTO();

        return dto;
    }

    public HallEntity dtoToEntity(final HallDTO dto) {
        if (dto == null)
            return null;

        final HallEntity entity = new HallEntity();

        return entity;
    }

}
