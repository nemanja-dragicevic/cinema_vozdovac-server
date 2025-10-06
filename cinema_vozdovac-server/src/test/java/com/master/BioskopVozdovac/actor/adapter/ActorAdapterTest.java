package com.master.BioskopVozdovac.actor.adapter;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.enums.Gender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.master.BioskopVozdovac.actor.adapter.ActorAdapter.*;
import static com.master.BioskopVozdovac.input.ActorData.ACTOR_DTO;
import static com.master.BioskopVozdovac.input.ActorData.ACTOR_ENTITY;
import static org.junit.jupiter.api.Assertions.*;

class ActorAdapterTest {

    @Test
    void testDtoToEntity() {
        ActorEntity entity = dtoToEntity(ACTOR_DTO);

        assertNotNull(entity);
        assertEquals(0L, entity.getActorID());
        assertEquals(Gender.MALE, entity.getGender());
        assertEquals("Jack", entity.getFirstName());
        assertEquals("Sparrow", entity.getLastName());
    }

    @Test
    void testDtoToEntityNull() {
        ActorEntity entity = dtoToEntity(null);
        assertNull(entity);
    }

    @Test
    void testEntityToDto() {
        ActorDTO dto = entityToDTO(ACTOR_ENTITY);

        assertNotNull(dto);
        assertEquals(Gender.MALE, dto.gender());
        assertEquals("Jack", dto.firstName());
        assertEquals("Sparrow", dto.lastName());
    }

    @Test
    void testToDTO() {
        List<ActorEntity> actors = List.of(ACTOR_ENTITY);
        List<ActorDTO> dtos = toDTO(actors);

        assertNotNull(dtos);
        assertEquals(actors.size(), dtos.size());
        assertEquals(Gender.MALE, dtos.get(0).gender());
        assertEquals("Jack", dtos.get(0).firstName());
        assertEquals("Sparrow", dtos.get(0).lastName());
    }

}
