package com.master.BioskopVozdovac.hall.adapter;

import com.master.BioskopVozdovac.hall.model.HallDTO;
import com.master.BioskopVozdovac.hall.model.HallEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HallAdapter {

    public HallDTO entityToDTO(final HallEntity entity) {
        if (entity == null)
            return null;

        final HallDTO dto = new HallDTO();
        dto.setHallID(entity.getHallID());
        dto.setHallName(entity.getHallName());

        return dto;
    }

    public HallEntity dtoToEntity(final HallDTO dto) {
        if (dto == null)
            return null;

        final HallEntity entity = new HallEntity();
        entity.setHallID(dto.getHallID());
        entity.setHallName(dto.getHallName());

        return entity;
    }

    public List<HallDTO> toDTO(final List<HallEntity> entities) {
        if (entities == null)
            return null;

        return entities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
