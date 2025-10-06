package com.master.BioskopVozdovac.actor.service;

import com.master.BioskopVozdovac.actor.model.ActorDTO;
import com.master.BioskopVozdovac.actor.model.ActorEntity;
import com.master.BioskopVozdovac.actor.repository.ActorRepository;
import com.master.BioskopVozdovac.enums.Gender;
import com.master.BioskopVozdovac.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.master.BioskopVozdovac.input.ActorData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;

    @Mock
    private ActorRepository actorRepository;

    @Test
    void testSaveActor() {
        when(actorRepository.save(any(ActorEntity.class)))
                .thenReturn(ACTOR_ENTITY);

        ActorDTO resultDto = actorService.saveActor(ACTOR_DTO);

        verify(actorRepository, times(1)).save(any(ActorEntity.class));

        assertEquals(ACTOR_ENTITY.getActorID(), resultDto.actorID());
        assertEquals(ACTOR_ENTITY.getFirstName(), resultDto.firstName());
        assertEquals(ACTOR_ENTITY.getLastName(), resultDto.lastName());
        assertEquals(ACTOR_ENTITY.getGender(), resultDto.gender());
    }

    @Test
    void testGetActorById() {
        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(ACTOR_ENTITY));

        ActorDTO found = actorService.getActorById(0L);

        assertNotNull(found);
        assertEquals("Jack", found.firstName());
        verify(actorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetActorByIdNotFound() {
        when(actorRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            actorService.getActorById(1L);
        });

        assertEquals("Could not find actor with id: 1", exception.getMessage());
        verify(actorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateActor() {
        when(actorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ACTOR_ENTITY));
        when(actorRepository.save(any(ActorEntity.class))).thenReturn(ACTOR_ENTITY);

        ActorDTO updated = actorService.updateActor(ACTOR_DTO);

        assertNotNull(updated);
        assertEquals(ACTOR_DTO.firstName(), updated.firstName());
        assertEquals(ACTOR_DTO.lastName(), updated.lastName());
        assertEquals(ACTOR_DTO.gender(), updated.gender());
    }

    @Test
    void testUpdateActorNotFound() {
        when(actorRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> {
            actorService.updateActor(ACTOR_DTO);
        });

        assertEquals("Actor not found", e.getMessage());
        verify(actorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteActor() {
        when(actorRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(actorRepository).deleteById(anyLong());

        Long actorID = 1L;
        String message = actorService.deleteActorById(actorID);

        verify(actorRepository, times(1)).deleteById(actorID);
        assertEquals("Successfully deleted actor with id: " + actorID, message);
    }

    @Test
    void testDeleteActorNotFound() {
        when(actorRepository.existsById(anyLong())).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            actorService.deleteActorById(1L);
        });

        assertEquals("Actor not found", exception.getMessage());
        verify(actorRepository, times(1)).existsById(anyLong());
    }

    @Test
    void testGetAll() {
        List<ActorEntity> entites = new ArrayList<>();
        entites.add(ACTOR_ENTITY);
        UPDATED_ACTOR_ENTITY.setActorID(2L);
        UPDATED_ACTOR_ENTITY.setGender(Gender.MALE);
        entites.add(UPDATED_ACTOR_ENTITY);

        List<ActorDTO> dtos = new ArrayList<>();
        dtos.add(ACTOR_DTO);
        dtos.add(UPDATED_ACTOR_DTO);

        when(actorRepository.findAll()).thenReturn(entites);

        List<ActorDTO> all = actorService.getAll();

        assertNotNull(all);
        assertEquals(2, all.size());

        verify(actorRepository, times(1)).findAll();

        assertEquals("Jack", all.get(0).firstName());
        assertEquals("Sparrow", all.get(0).lastName());

        assertEquals(2L, all.get(1).actorID());
        assertEquals("Jackie", all.get(1).firstName());
        assertEquals("Chan", all.get(1).lastName());
    }

}