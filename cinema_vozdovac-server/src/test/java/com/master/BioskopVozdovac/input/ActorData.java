package com.master.BioskopVozdovac.input;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.enums.Gender;

public class ActorData {

    public static final ActorEntity ACTOR_ENTITY = ActorEntity.builder()
            .actorID(0L)
            .firstName("Jack")
            .lastName("Sparrow")
            .gender(Gender.MALE)
            .build();

    public static final ActorDTO ACTOR_DTO = ActorDTO.builder()
            .actorID(0L)
            .firstName("Jack")
            .lastName("Sparrow")
            .gender(Gender.MALE)
            .build();

    public static final ActorEntity UPDATED_ACTOR_ENTITY = ACTOR_ENTITY.toBuilder()
            .actorID(0L)
            .firstName("Jackie")
            .lastName("Chan")
            .gender(Gender.MALE)
            .build();

    public static final ActorDTO UPDATED_ACTOR_DTO = ActorDTO.builder()
            .firstName("Jackie")
            .lastName("Chan")
            .build();

}
