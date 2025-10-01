package com.master.BioskopVozdovac.actor.adapter;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;

import java.util.List;

public final class ActorAdapter {

    private ActorAdapter(){
        throw new AssertionError();
    }

    public static ActorEntity dtoToEntity(final ActorDTO dto) {
        if (dto == null)
            return null;

        return ActorEntity.create(dto.actorID(), dto.firstName(), dto.lastName(), dto.gender());
    }

    public static ActorDTO entityToDTO(final ActorEntity entity) {
        if (entity == null)
            return null;

        return ActorDTO.builder()
                .actorID(entity.getActorID())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .build();
    }

    public static List<ActorDTO> toDTO(final List<ActorEntity> entities) {
        if (entities == null)
            return List.of();

        return entities.stream().map(ActorAdapter::entityToDTO).toList();
    }

}
