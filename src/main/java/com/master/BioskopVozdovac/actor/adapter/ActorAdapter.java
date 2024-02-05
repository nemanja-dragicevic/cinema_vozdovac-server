package com.master.BioskopVozdovac.actor.adapter;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import org.springframework.stereotype.Component;

@Component
public class ActorAdapter {

    public ActorEntity dtoToEntity(final ActorDTO dto) {
        if (dto == null)
            return null;

        final ActorEntity entity = new ActorEntity();

        entity.setActorID(dto.getActorID());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setGender(dto.getGender());

        return entity;
    }

    public ActorDTO entityToDTO(final ActorEntity entity) {
        if (entity == null)
            return null;

        final ActorDTO dto = new ActorDTO();

        dto.setActorID(entity.getActorID());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setGender(entity.getGender());

        return dto;
    }

}
